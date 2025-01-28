package com.example.s_and_c.Service.Impl;


import com.example.s_and_c.DTO.FormDTO.FormResponseDTO;
import com.example.s_and_c.DTO.FormDTO.FormWithStudentsDTO;
import com.example.s_and_c.DTO.InternshipDTOs.*;
import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import com.example.s_and_c.Entities.*;
import com.example.s_and_c.Entities.Status.FormType;
import com.example.s_and_c.Exception.ResourceNotFoundException;
import com.example.s_and_c.Mapper.InternshipMapper;
import com.example.s_and_c.Repositories.*;
import com.example.s_and_c.Service.InternshipService;
import com.example.s_and_c.Utils.InternshipException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;

@Service
@AllArgsConstructor
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;
    private final StudentRepository studentRepository;
    private final FormRepository formRepository;
    private final QualificationRepository qualificationRepository;
    private final EntityManager entityManager;


    @Transactional
    @Override
    public List<InternshipDTO> createInternship(String email, InsertInternshipDTO insertInternshipDTO) {

        Company insertingCompany = companyRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Company not found"));

        Internship internship = InternshipMapper.maptoInternship(insertInternshipDTO, insertingCompany);
        internshipRepository.save(internship);
        formRepository.saveAll(internship.getForm());
        qualificationRepository.saveAll(internship.getQualification_required());


        return getAllInternshipsByEmail(insertingCompany);
    }

    private List<InternshipDTO> getAllInternshipsByEmail(Company insCompany) {
        List<Internship> internships = internshipRepository.findByCompany(insCompany);
        List<ShortStudentDTO> appliedStudents = new ArrayList<>();
        List<ShortStudentDTO> acceptedStudents = new ArrayList<>();
        List<ShortStudentDTO> selectedStudents = new ArrayList<>();
        List<InternshipDTO> allInternships = new ArrayList<>();
        for (Internship internship : internships) {
            for(Student student : internship.getAppliedStudents()){
                createShortenDtoList(appliedStudents, student);
            }
            for(Student student : internship.getAppliedStudents()){
                createShortenDtoList(acceptedStudents, student);

            }
            for(Student student : internship.getSelectedStudents()){
                createShortenDtoList(selectedStudents, student);

            }
            allInternships.add(InternshipMapper.maptoInternshipDTO(internship,appliedStudents,acceptedStudents,selectedStudents));
        }
        return allInternships;

    }

    private void createShortenDtoList(List<ShortStudentDTO> selectedStudents, Student student) {
        List<Form> cv = formRepository.findByStudentAndFormType(student, FormType.CV);
        List<String> cv_string = new ArrayList<>();
        for(Form form : cv){
            cv_string.add(form.getResponse());
        }
        selectedStudents.add(new ShortStudentDTO(student.getEmail(), student.getName(), student.getSurname(), cv_string));
    }


    @Override
    public List<InternshipForStudentsDTO> findMatch(SearchDTO searchDTO) {
        List<Internship> results = new ArrayList<>();
        if(searchDTO.getKeyword() != null)
            results = internshipRepository.findInternshipsBySearch(searchDTO.getKeyword(),searchDTO.getMinStart(),searchDTO.getMaxEnd(),searchDTO.getMinSalary());

        List<InternshipForStudentsDTO> internshipDTOS = new ArrayList<>();
        for (Internship internship : results) {
            InternshipForStudentsDTO internshipDTO = InternshipMapper.maptoInternshipForStudentsDTO(internship);
            internshipDTOS.add(internshipDTO);
        }
        return internshipDTOS;
    }

    @Override
    public List<InternshipCompleteDTO> getMyInternship(String email) {
        Company company = companyRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Company not found"));
            List<FormWithStudentsDTO> compiledForms = new ArrayList<>();
            List<Internship> internships = internshipRepository.findByCompany(company);
            List<InternshipCompleteDTO> internshipCompleteDTOS = new ArrayList<>();
            for (Internship internship : internships) {
                for(Student student: internship.getAcceptedStudents()){
                    List<Form> results = formRepository.findByInternshipAndStudentAndFormType(internship, student,FormType.INTERVIEW);
                    for(Form form: results){
                        compiledForms.add(new FormWithStudentsDTO(form.getFormId(), form.getRequest(), form.getResponse(),
                                new ShortStudentDTO(student.getEmail(), student.getName(), student.getSurname())));
                    }
                }
                internshipCompleteDTOS.add(InternshipMapper.maptoInternshipCompleteDTO(internship,compiledForms));
            }

            return internshipCompleteDTOS;


    }

    @Override
    public void addAcceptedStudent(String email, int internshipId, String authEmail) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new InternshipException("Student not found", 404));

        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new InternshipException("Internship not found", 404));

        if (internship.getAcceptedStudents().contains(student) ||
                internship.getSelectedStudents().contains(student)) {
            throw new InternshipException("Student already accepted", 409);
        }

        if (!authEmail.equals(internship.getCompany().getEmail())) {
            throw new InternshipException("Student does not belong to this company", 401);
        }

        if (!internship.getAppliedStudents().contains(student)) {
            throw new InternshipException("Student has not applied for this internship", 400);
        }

        internship.addAcceptedStudent(student);
        internship.deleteAppliedStudent(student);

        List<Form> templateForms = formRepository.findByInternshipAndCompanyAndFormType(
                internship,
                internship.getCompany(),
                FormType.INTERVIEW
        );

        List<Form> newForms = new ArrayList<>();
        for (Form template : templateForms) {
            Form newForm = new Form();
            newForm.setFormType(FormType.INTERVIEW);
            newForm.setInternship(internship);
            newForm.setRequest(template.getRequest());
            newForm.setStudent(student);
            newForm.setResponse(null);
            newForm.setCompany(internship.getCompany());
            newForms.add(newForm);
        }

        internshipRepository.save(internship);
        formRepository.saveAll(newForms);
    }

    @Override
    public List<InternshipForStudentsDTO> getAllForStudents() {
        List<Internship> internships = internshipRepository.findAll();
        List<InternshipForStudentsDTO> internshipForStudentsDTOS = new ArrayList<>();
        for(Internship internship : internships){
            internshipForStudentsDTOS.add(InternshipMapper.maptoInternshipForStudentsDTO(internship));
        }
        return internshipForStudentsDTOS;
    }

    @Override
    public void addFormResponse(FormResponseDTO formResponseDTO, String authEmail) {
        System.out.println(formResponseDTO.getInternshipId());
        Internship internship = internshipRepository.findInternshipByInternshipId(formResponseDTO.getInternshipId())
                .orElseThrow(()-> new IllegalArgumentException("Internship not found"));
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new IllegalArgumentException("Student not found"));
        List<Form> form = formRepository.findByInternshipAndStudentAndFormType(internship,student,FormType.INTERVIEW);
        for(FormDTO formDTO : formResponseDTO.getFormToCompile()){
            for(Form forms : form){
                if(forms.getFormId() == formDTO.getFormId()){
                    forms.setResponse(formDTO.getResponse());
                    formRepository.save(forms);
                }

            }

        }
        internshipRepository.save(internship);
    }

    @Override
    public void addSelectedStudent(String email, int internshipId, String authEmail) {
        Company company = companyRepository.findByEmail(authEmail).orElseThrow(()->new IllegalArgumentException("Company not found"));
        List<Internship> internships = internshipRepository.findByCompany(company);
        Internship internship = internshipRepository.findById(internshipId).orElseThrow(()->new IllegalArgumentException("Internship not found"));
        if(!internships.contains(internship)){
            throw new IllegalArgumentException("Internship not found");
        }
        Student student = studentRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("Student not found"));
        internship.addSelectedStudent(student);
        internship.deleteAcceptedStudent(student);
        internshipRepository.save(internship);
    }
}
