package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.DTO.AuthDTOs.AuthRequestDTO;
import com.example.s_and_c.DTO.CompanyDTOs.CompanyDTO;
import com.example.s_and_c.DTO.CompanyDTOs.UpdatedCompanyDTO;
import com.example.s_and_c.DTO.AuthDTOs.UserTokenDTO;
import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;
import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.InternshipDTOs.FormDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;
import com.example.s_and_c.Entities.*;
import com.example.s_and_c.Entities.Status.FormType;
import com.example.s_and_c.Entities.Status.Role;
import com.example.s_and_c.Exception.ResourceNotFoundException;
import com.example.s_and_c.Mapper.CompanyMapper;
import com.example.s_and_c.Mapper.FormMapper;
import com.example.s_and_c.Repositories.*;
import com.example.s_and_c.Service.CompanyService;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private CompanyRepository companyRepository;
    private InternshipRepository internshipRepository;
    private final AuthService authService;
    private final FormRepository formRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {

        Company company = CompanyMapper.mapToCompany(companyDTO);
        Company savedCompany = companyRepository.save(company);

        return CompanyMapper.mapToCompanyDTO(savedCompany);
    }

    @Override
    public CompanyDTO getCompany(String email) {
        Company company = companyRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Company with id " + email + " not found"));
        return CompanyMapper.mapToCompanyDTO(company);
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(CompanyMapper::mapToCompanyDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UpdatedCompanyDTO updateCompany(String email, @NotNull CompanyDTO companyDTO) {
        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Company with email " + email + " not found"));

        try {
            if (!companyDTO.getEmail().equals(company.getEmail())) {
                if(studentRepository.findByEmail(companyDTO.getEmail()).isPresent()) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,"Student with email " + companyDTO.getEmail() + " already exists");
                }
                Company newCompany = new Company();
                newCompany.setEmail(companyDTO.getEmail());
                newCompany.setName(companyDTO.getName());
                if(!company.getVat_number().equals(companyDTO.getVat_number())) {
                    newCompany.setVat_number(companyDTO.getVat_number());
                }

                newCompany.setDescription(companyDTO.getDescription());
                String rawPassword = companyDTO.getPassword();
                newCompany.setPassword(passwordEncoder.encode(rawPassword));
                newCompany.setRole(Role.COMPANY);

                entityManager.persist(newCompany);
                entityManager.flush();

                // 3. Aggiorna i riferimenti nelle internship
                Query query = entityManager.createQuery(
                        "UPDATE Internship i SET i.company = :newCompany WHERE i.company = :oldCompany");
                query.setParameter("newCompany", newCompany);
                query.setParameter("oldCompany", company);
                query.executeUpdate();

                // 4. Rimuovi la vecchia company
                entityManager.remove(company);
                entityManager.flush();
                newCompany.setVat_number(companyDTO.getVat_number());
                UserTokenDTO user = authService.authenticate(
                        new AuthRequestDTO(newCompany.getEmail(), rawPassword)
                );

                String token = user.getToken();
                return CompanyMapper.mapToUpdatedCompanyDTO(newCompany, token);
            }

            if (!companyDTO.getName().equals(company.getName())) {
                company.setName(companyDTO.getName());
            }
            if (!companyDTO.getVat_number().equals(company.getVat_number())) {
                company.setVat_number(companyDTO.getVat_number());
            }
            if (!companyDTO.getDescription().equals(company.getDescription())) {
                company.setDescription(companyDTO.getDescription());
            }

            if (!passwordEncoder.matches(companyDTO.getPassword(), company.getPassword())) {
                String rawPassword = companyDTO.getPassword();
                company.setPassword(passwordEncoder.encode(rawPassword));

                UserTokenDTO user = authService.authenticate(
                        new AuthRequestDTO(company.getEmail(), rawPassword)
                );
                String token = user.getToken();
                return CompanyMapper.mapToUpdatedCompanyDTO(companyRepository.save(company), token);
            }

            return CompanyMapper.mapToUpdatedCompanyDTO(companyRepository.save(company));

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Inserted Data violate constraint");
        }
    }




    @Override
    public void deleteCompany(String email){
        companyRepository.deleteCompanyByEmail(email);
    }
    /**
     * @param authEmail
     * @param internshipId
     * @return
     */
    @Override
    public List<ComplaintDTO> handleComplaintToSend(String authEmail, int internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElseThrow(()->new RuntimeException("Internship not found"));
        if(!internship.getCompany().getEmail().equals(authEmail)){
            throw new DataIntegrityViolationException("Internship does not belong to this company");
        }
        Form form = new Form();
        form.setFormType(FormType.COMPLAINT);
        form.setRequest("Tell us your complaint:");
        form.setResponse(null);
        form.setCompany(internship.getCompany());
        form.setInternship(internship);
        formRepository.save(form);
        List<Form> formList = formRepository.findByInternshipAndCompanyAndFormType(internship, internship.getCompany(),FormType.COMPLAINT);
        List<ComplaintDTO> complaintDTOList = new ArrayList<>();
        for(Form formIter : formList){
            complaintDTOList.add(FormMapper.mapToComplaintDTO(formIter));
        }
        return complaintDTOList;
    }

    @Override
    public void handleComplaint(String authEmail, ComplaintDTO complaintDTO) {
        Company company = companyRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));
        Internship internship = internshipRepository.findById(complaintDTO.getInternship_id()).orElseThrow(()->new RuntimeException("Internship not found"));
        List<Form> formList = formRepository.findByInternshipAndCompanyAndFormType(internship, company, FormType.COMPLAINT);
        for (Form form : formList) {
            for( FormDTO formDTO : complaintDTO.getComplaints()){
                if(formDTO.getFormId() == form.getFormId()){
                    form.setResponse(formDTO.getResponse());
                    formRepository.save(form);
                }
            }
        }

    }

    /**
     * @param authEmail
     * @param feedBackDTO
     */
    @Override
    public void handleFeedBack(String authEmail, FeedBackDTO feedBackDTO) {
        Company company = companyRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));
        Internship internship = internshipRepository.findById(feedBackDTO.getInternship_id()).orElseThrow(()->new RuntimeException("Internship not found"));
        List<Form> formList = formRepository.findByInternshipAndCompanyAndFormType(internship, company,FormType.FEEDBACK);
        for (Form form : formList) {
            for( FormDTO formDTO : feedBackDTO.getFeedbacks()){
                if(formDTO.getFormId() == form.getFormId()){
                    form.setResponse(formDTO.getResponse());
                    formRepository.save(form);
                }
            }
        }
    }

    /**
     * @param authEmail
     * @param internshipId
     * @return
     */
    @Override
    public List<FeedBackDTO> handleFeedBackToSend(String authEmail, int internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElseThrow(()->new RuntimeException("Internship not found"));
        if(!internship.getCompany().getEmail().equals(authEmail)){
            throw new DataIntegrityViolationException("Internship does not belong to this company");
        }
        List<String> requests = new ArrayList<>();
        requests.add("The service/product met my expectations:");
        requests.add("I found the experience user-friendly and intuitive");
        requests.add("The staff/team was helpful and professional.");
        requests.add("I would recommend this service/product to others");
        requests.add("I am satisfied with the overall quality.");
        for(String request : requests){
            Form form = new Form();
            form.setFormType(FormType.FEEDBACK);
            form.setRequest(request);
            form.setResponse(null);
            form.setCompany(internship.getCompany());
            form.setInternship(internship);
            formRepository.save(form);
        }

        List<Form> formList = formRepository.findByInternshipAndCompanyAndFormType(internship, internship.getCompany(),FormType.FEEDBACK);
        List<FeedBackDTO> feedBackDTOList = new ArrayList<>();
        for(Form formIter : formList){
            feedBackDTOList.add(FormMapper.mapToFeedBackDTO(authEmail,formIter));
        }
        return feedBackDTOList;
    }

    /**
     * @param authEmail
     * @param internshipId
     * @return
     */
    @Override
    public List<ReviewDTO> handleReviewToSend(String authEmail, int internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElseThrow(()->new RuntimeException("Internship not found"));
        if(!internship.getCompany().getEmail().equals(authEmail)){
            throw new DataIntegrityViolationException("Internship does not belong to this company");
        }
        List<String> requests = new ArrayList<>();
        requests.add("How do you rate this experience?");
        requests.add("What are your suggestions?");
        for(String request : requests){
            Form form = new Form();
            form.setFormType(FormType.REVIEW);
            form.setRequest(request);
            form.setResponse(null);
            form.setCompany(internship.getCompany());
            form.setInternship(internship);
            formRepository.save(form);
        }
        List<Form> formList = formRepository.findByInternshipAndCompanyAndFormType(internship, internship.getCompany(),FormType.REVIEW);
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for(Form formIter : formList){
            reviewDTOList.add(FormMapper.mapToReviewDTO(authEmail,formIter));
        }
        return reviewDTOList;
    }

    /**
     * @param authEmail
     * @param reviewDTO
     */
    @Override
    public void handleReview(String authEmail, ReviewDTO reviewDTO) {
        Company company = companyRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));
        Internship internship = internshipRepository.findById(reviewDTO.getInternship_id()).orElseThrow(()->new RuntimeException("Internship not found"));
        List<Form> formList = formRepository.findByInternshipAndCompanyAndFormType(internship, company, FormType.REVIEW);
        for (Form form : formList) {
            for( FormDTO formDTO : reviewDTO.getReview()){
                if(formDTO.getFormId() == form.getFormId()){
                    form.setResponse(formDTO.getResponse());
                    formRepository.save(form);
                }
            }
        }

    }


}
