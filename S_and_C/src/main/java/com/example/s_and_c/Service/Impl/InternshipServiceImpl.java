package com.example.s_and_c.Service.Impl;


import com.example.s_and_c.DTO.FormDTO.FormDTO;
import com.example.s_and_c.DTO.FormDTO.FormResponseDTO;
import com.example.s_and_c.DTO.FormDTO.FormWithStudentsDTO;
import com.example.s_and_c.DTO.InternshipDTOs.*;
import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import com.example.s_and_c.Entities.*;
import com.example.s_and_c.Entities.Status.FormType;
import com.example.s_and_c.Mapper.InternshipMapper;
import com.example.s_and_c.Repositories.*;
import com.example.s_and_c.Service.InternshipService;
import com.example.s_and_c.Utils.InternshipException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;
    private final StudentRepository studentRepository;
    private final FormRepository formRepository;
    private final QualificationRepository qualificationRepository;


    @Transactional
    @Override
    public List<InternshipDTO> createInternship(String email, InsertInternshipDTO insertInternshipDTO) {

        Company insertingCompany = companyRepository.findByEmail(email).orElseThrow(()-> new InternshipException("Company not found",404));

        Internship internship = InternshipMapper.maptoInternship(insertInternshipDTO, insertingCompany);
        if(LocalDate.now().isAfter(internship.getStartDate()) ||
                LocalDate.now().isAfter(internship.getEndDate()) ||
                LocalDate.now().isAfter(internship.getEndFormCompilingDate()) ||
                LocalDate.now().isAfter(internship.getEndSelectionAcceptanceDate())) {
            throw new InternshipException("Not correct dates",400);
        }
        if(internship.getEndFormCompilingDate().isBefore(internship.getEndSelectionAcceptanceDate() )&&
                internship.getEndSelectionAcceptanceDate().isBefore(internship.getStartDate()) &&
                internship.getStartDate().isBefore(internship.getEndDate())) {
            internshipRepository.save(internship);
            formRepository.saveAll(internship.getForm());
            qualificationRepository.saveAll(internship.getQualification_required());
            return getAllInternshipsByEmail(insertingCompany);
        }
        else
            throw new InternshipException("Not correct dates",400);

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
        selectedStudents.add(new ShortStudentDTO(student.getEmail(), student.getName(), student.getSurname(), student.getDescription(), cv_string));
    }


    @Override
    public List<InternshipForStudentsDTO> findMatch(SearchDTO searchDTO) {
        List<Internship> results = new ArrayList<>();
        if(searchDTO.getKeyword() != null)
            results = internshipRepository.findInternshipsBySearch(searchDTO.getKeyword(),searchDTO.getMinStart(),searchDTO.getMaxEnd(),searchDTO.getMinSalary());

        List<InternshipForStudentsDTO> internshipDTOS = new ArrayList<>();
        for (Internship internship : results) {
            InternshipForStudentsDTO internshipDTO = InternshipMapper.maptoInternshipForStudentsDTO(internship, false, false, false);
            internshipDTOS.add(internshipDTO);
        }
        return internshipDTOS;
    }

    @Override
    public List<InternshipCompleteDTO> getMyInternship(String email) {
        Company company = companyRepository.findByEmail(email).orElseThrow(()->new InternshipException("Company not found",404));

            List<Internship> internships = internshipRepository.findByCompany(company);
            List<InternshipCompleteDTO> internshipCompleteDTOS = new ArrayList<>();


            for (Internship internship : internships) {
                if(LocalDate.now().isAfter(internship.getEndFormCompilingDate())){
                    internshipRepository.deleteByAppliedStudentsContainingIgnoreCase(internship.getAppliedStudents());
                    internshipRepository.deleteByAcceptedStudentsContainingIgnoreCase(internship.getAcceptedStudents());
                }
                //appliedStudent
                List<ShortStudentDTO> appliedStudents = new ArrayList<>();

                for(Student student: internship.getAppliedStudents()){
                List<Form> form_cv = formRepository.findByInternshipAndStudentAndFormType(internship, student,FormType.CV);
                List<String> cv = new ArrayList<>();
                for(Form form : form_cv){
                    cv.add(form.getResponse());
                }
                appliedStudents.add(new ShortStudentDTO(student.getEmail(), student.getName(), student.getSurname(), student.getDescription(), cv));
                }
                List<FormWithStudentsDTO> compiledForms = new ArrayList<>();

                //acceptedStudent
                for(Student student: internship.getAcceptedStudents()){
                    List<Form> form_cv = formRepository.findByInternshipAndStudentAndFormType(internship, student,FormType.CV);
                    List<String> cv = new ArrayList<>();
                    for(Form form : form_cv){
                        cv.add(form.getResponse());
                    }
                    List<Form> results = formRepository.findByInternshipAndStudentAndFormType(internship, student,FormType.INTERVIEW);
                    for(Form form: results){
                        compiledForms.add(new FormWithStudentsDTO(form.getFormId(), form.getRequest(), form.getResponse(),
                                new ShortStudentDTO(student.getEmail(), student.getName(), student.getSurname(), student.getDescription(), cv)));
                    }
                }
                //selectedStudents
                List<ShortStudentDTO> selectedStudents = new ArrayList<>();
                for(Student student: internship.getSelectedStudents()){
                    selectedStudents.add(new ShortStudentDTO(student.getEmail(), student.getName(), student.getSurname(),student.getDescription()));
                }

                internshipCompleteDTOS.add(InternshipMapper.maptoInternshipCompleteDTO(internship,compiledForms,appliedStudents,selectedStudents));
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
            Form newForm = new Form(template,student);
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
            internshipForStudentsDTOS.add(InternshipMapper.maptoInternshipForStudentsDTO(internship, false, false, false));
        }
        return internshipForStudentsDTOS;
    }

    @Override
    public void addFormResponse(FormResponseDTO formResponseDTO, String authEmail) {
        Internship internship = internshipRepository.findInternshipByInternshipId(formResponseDTO.getInternshipId())
                .orElseThrow(()-> new InternshipException("Internship not found",404));

        Student student = studentRepository.findByEmail(authEmail)
                .orElseThrow(()->new InternshipException("Student not found",404));

        List<Form> forms = formRepository.findByInternshipAndStudentAndFormType(internship, student, FormType.INTERVIEW);

        Map<Long, Form> formMap = forms.stream()
                .collect(Collectors.toMap(Form::getFormId, form -> form));

        for (FormDTO formDTO : formResponseDTO.getFormToCompile()) {
            Form form = formMap.get(formDTO.getFormId());
            if (form != null) {
                form.setResponse(formDTO.getResponse());
            }
        }

        formRepository.saveAll(forms);
    }

    /**
     * @param internshipId
     * @param authEmail
     */
    @Override
    public void renounce(long internshipId, String authEmail) {
        Internship internship = internshipRepository.findInternshipByInternshipId(internshipId).orElseThrow(()->new InternshipException("Internship not found",404));
        if(LocalDate.now().isAfter(internship.getStartDate()))
            throw new InternshipException("Internship already started", 400);
        Student student = studentRepository.findByEmail(authEmail)
                .orElseThrow(() -> new InternshipException("Student not found", 404));
        if(!internship.getSelectedStudents().contains(student)){
            throw new InternshipException("Student has not been selected to this internship", 404);
        }
        internship.deleteSelectedStudent(student);
        internshipRepository.save(internship);
    }

    @Transactional
    @Override
    public void addSelectedStudent(String email, long internshipId, String authEmail) {
        Company company = companyRepository.findByEmail(authEmail).orElseThrow(
                ()->new InternshipException("Company not found",404));

        List<Internship> internships = internshipRepository.findByCompany(company);
        Internship internship = internshipRepository.findInternshipByInternshipId(internshipId).orElseThrow(
                ()->new InternshipException("Internship not found",404));
        if(!internships.contains(internship)){
            throw new InternshipException("Internship not found",404);
        }
        Student student = studentRepository.findByEmail(email).orElseThrow(
                ()->new InternshipException("Student not found",404));
        if (internship.getSelectedStudents().contains(student)) {
            throw new InternshipException("Student already accepted", 409);
        }

        if (!authEmail.equals(internship.getCompany().getEmail())) {
            throw new InternshipException("Student does not belong to this company", 401);
        }

        if (internship.getAppliedStudents().contains(student) || !internship.getAcceptedStudents().contains(student)) {
            throw new InternshipException("Student has not been selected for this internship", 400);
        }

        internship.addSelectedStudent(student);
        internship.deleteAcceptedStudent(student);
        Internship savedInternship = internshipRepository.save(internship);
        if (savedInternship.getSelectedStudents().isEmpty()) {
            throw new InternshipException("Save failed", 500);
        }
    }
}
