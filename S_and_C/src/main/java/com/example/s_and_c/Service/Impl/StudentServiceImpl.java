package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.DTO.AuthDTOs.AuthRequestDTO;
import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;
import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.FormDTO.FormCompleteDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipForStudentsDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;
import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.UpdatedStudentDTO;
import com.example.s_and_c.DTO.AuthDTOs.UserTokenDTO;
import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Entities.Status.FormType;
import com.example.s_and_c.Entities.Status.Role;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Exception.ResourceNotFoundException;
import com.example.s_and_c.Mapper.FormMapper;
import com.example.s_and_c.Mapper.InternshipMapper;
import com.example.s_and_c.Mapper.StudentMapper;
import com.example.s_and_c.Repositories.CompanyRepository;
import com.example.s_and_c.Repositories.FormRepository;
import com.example.s_and_c.Repositories.InternshipRepository;
import com.example.s_and_c.Repositories.StudentRepository;
import com.example.s_and_c.Service.StudentService;
import com.example.s_and_c.Utils.DateUtils;
import com.example.s_and_c.Utils.InternshipException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final AuthService authService;
    private final FormRepository formRepository;
    private final CompanyRepository companyRepository;
    private StudentRepository studentRepository;
    private InternshipRepository internshipRepository;
    private final PasswordEncoder passwordEncoder;
    private final DateUtils dateUtils;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public StudentDTO getStudent(String email) {
        Student student = studentRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Student with id " + email + " not found"));
        List<Form> formsCV = formRepository.findByStudentAndFormType(student, FormType.CV);
        List<String> formDTOS = new ArrayList<>();
        for(Form form : formsCV)
            formDTOS.add(form.getResponse());
        return StudentMapper.mapToStudentDTO(student,formDTOS);
    }

    /*@Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for(Student student : students){
            List<Form> formsCV = formRepository.findByStudentAndFormType(student, FormType.CV);
            List<String> formDTOS = new ArrayList<>();
            for(Form form : formsCV)
                formDTOS.add(FormMapper.mapToFormDTO(form));
            studentDTOS.add(StudentMapper.mapToStudentDTO(student,formDTOS));
        }
        return studentDTOS;
    }*/

    @Transactional
    @Override
    public UpdatedStudentDTO updateStudent(String email, @NotNull UpdatedStudentDTO studentDTO) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + email + " not found"));
        List<Form> forms = formRepository.findByStudentAndFormType(student, FormType.CV);
        try {
            System.out.println(studentDTO.getEmail());
            if (!studentDTO.getEmail().equals(student.getEmail())) {
                if(companyRepository.findByEmail(studentDTO.getEmail()).isPresent())
                    throw new InternshipException("company with email " + studentDTO.getEmail() + " already exists",409);
                Student newStudent = new Student();
                newStudent.setEmail(studentDTO.getEmail());
                newStudent.setName(studentDTO.getName());
                newStudent.setSurname(studentDTO.getSurname());
                newStudent.setDescription(studentDTO.getDescription());
                newStudent.setRole(Role.STUDENT);
                newStudent.setPassword(passwordEncoder.encode(studentDTO.getPassword()));



                entityManager.persist(newStudent);
                entityManager.flush();

                formRepository.deleteAll(forms);
                formRepository.flush();
                for(String response : studentDTO.getCv()){
                    Form newForm = new Form();
                    newForm.setFormType(FormType.CV);
                    newForm.setStudent(newStudent);
                    newForm.setResponse(response);
                    formRepository.save(newForm);
                }

                entityManager.remove(student);
                entityManager.flush();
                studentRepository.save(newStudent);


                UserTokenDTO user = authService.authenticate(new AuthRequestDTO(newStudent.getEmail(), studentDTO.getPassword()));
                String token = user.getToken();
                return StudentMapper.mapToUpdatedStudentDTO(newStudent,forms, token);
            }

            System.out.println(studentDTO.getName());
            if (!studentDTO.getName().equals(student.getName())) {
                student.setName(studentDTO.getName());
                studentRepository.save(student);
            }

            System.out.println(studentDTO.getSurname());
            if (!studentDTO.getSurname().equals(student.getSurname())) {
                student.setSurname(studentDTO.getSurname());
                studentRepository.save(student);
            }

            if (!studentDTO.getDescription().equals(student.getDescription())) {
                student.setDescription(studentDTO.getDescription());
                studentRepository.save(student);
            }
            List<Form> form = new ArrayList<>();
            if(!studentDTO.getCv().isEmpty()){
                formRepository.deleteAll(forms);
                formRepository.flush();
                for(String response : studentDTO.getCv()){
                    Form newForm = new Form();
                    newForm.setFormType(FormType.CV);
                    newForm.setStudent(student);
                    newForm.setResponse(response);
                    form.add(newForm);
                    formRepository.save(newForm);
                }
            }

            String password = passwordEncoder.encode(studentDTO.getPassword());
            if (!student.getPassword().equals(password)) {
                student.setPassword(password);
                studentRepository.save(student);
                UserTokenDTO user = authService.authenticate(new AuthRequestDTO(student.getEmail(), password));
                String token = user.getToken();
                if(form.isEmpty())
                    return StudentMapper.mapToUpdatedStudentDTO(student,forms, token);
                else
                    return StudentMapper.mapToUpdatedStudentDTO(student,form, token);
            }
            return StudentMapper.mapToUpdatedStudentDTO(student, forms);
        } catch (InternshipException e) {
            throw new InternshipException("Inserted Data violate constraint",409);
        }
    }

    @Override
    public void requestInternship(long internshipId, String authEmail) {
        Internship internship = internshipRepository.findById((int)internshipId).orElseThrow(()->new InternshipException("Internship not found",404));
        if (LocalDate.now().isAfter(internship.getEndFormCompilingDate())) {
            throw new InternshipException("form compiling time is expired",400);
        }

        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new InternshipException("Student not found",404));
        if(internship.getAcceptedStudents().contains(student) || internship.getSelectedStudents().contains(student) || internship.getAppliedStudents().contains(student)){
            throw new InternshipException("Student already inserted",409);
        }
        try {
            internship.addAppliedStudent(student);
            internshipRepository.save(internship);
        } catch (Exception e) {
            throw new InternshipException("Error saving internship request", 500);
        }
    }

    @Override
    public List<InternshipForStudentsDTO> getPersonalInternships(String authEmail) {
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new InternshipException("Student not found",404));
        List<Internship> internshipsApplied = internshipRepository.findByAppliedStudentsContainingIgnoreCase(student);
        List<Internship> internshipsAccepted = internshipRepository.findByAcceptedStudentsContainingIgnoreCase(student);
        List<Internship> internshipsSelected = internshipRepository.findBySelectedStudentsContainingIgnoreCase(student);
        List<InternshipForStudentsDTO> internshipDTOList = new ArrayList<>();
        boolean isApplied = true,isAccepted = true,isSelected = true;

        for(Internship internship : internshipsApplied){
            if(LocalDate.now().isAfter(internship.getEndFormCompilingDate())){
                internshipRepository.deleteByAppliedStudentsContainingIgnoreCase(student);
            }
            internshipDTOList.add(InternshipMapper.maptoInternshipForStudentsDTO(internship, isApplied, !isAccepted, !isSelected));
        }
        for(Internship internship : internshipsAccepted){
            if(LocalDate.now().isAfter(internship.getEndFormCompilingDate())){
                internshipRepository.deleteByAcceptedStudentsContainingIgnoreCase(student);
            }
            List<Form> forms = formRepository.findByInternshipAndStudentAndFormType(internship, student, FormType.INTERVIEW );
            internshipDTOList.add(InternshipMapper.mapToInternshipForAcceptedStudentDTO(internship,forms, !isApplied, isAccepted, !isSelected));
        }
        for(Internship internship : internshipsSelected){
            List<Form> forms = formRepository.findByInternshipAndStudentAndFormType(internship, student, FormType.S_REVIEW );
            forms.addAll(formRepository.findByInternshipAndStudentAndFormType(internship, student, FormType.S_FEEDBACK ));
            forms.addAll(formRepository.findByInternshipAndStudentAndFormType(internship, student, FormType.S_COMPLAINT ));
            internshipDTOList.add(InternshipMapper.mapToInternshipForAcceptedStudentDTO(internship,forms, !isApplied, !isAccepted, isSelected));
        }
        return internshipDTOList;
    }


    @Override
    public void handleComplaintReceived(String authEmail, ComplaintDTO complaintDTO) {
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new InternshipException("Student not found",404));
        Internship internship = internshipRepository.findInternshipByInternshipId(complaintDTO.getInternshipId()).orElseThrow(()->new InternshipException("Internship not found",404));
        CompanyServiceImpl.checkComplaint(complaintDTO, internship, student);
        setComplaint(complaintDTO, student, internship, formRepository);


        /*
        //Code for new methods
        List<Form> formList = formRepository.findByInternshipAndStudentAndFormType(internship,student, FormType.COMPLAINT);
        List<ComplaintDTO> complaintDTOList = new ArrayList<>();
        for(Form formIter : formList){
            complaintDTOList.add(FormMapper.mapToComplaintDTO(formIter));
        }*/

    }

    static void setComplaint(ComplaintDTO complaintDTO, Student student, Internship internship, FormRepository formRepository) {
        Form form = new Form();
        form.setFormType(FormType.S_COMPLAINT);
        form.setRequest("Tell us your complaint:");
        form.setResponse(complaintDTO.getComplaint());
        form.setStudent(student);
        form.setCompany(internship.getCompany());
        form.setInternship(internship);
        formRepository.save(form);
    }

    /**
     * @param authEmail
     * @param feedBackDTO
     */
    @Override
    public void handleFeedBack(String authEmail, FeedBackDTO feedBackDTO) {
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));
        Internship internship = internshipRepository.findInternshipByInternshipId(feedBackDTO.getInternshipId()).orElseThrow(()->new RuntimeException("Internship not found"));
        if(!internship.getSelectedStudents().contains(student) || feedBackDTO.getFeedbacks().isEmpty()){
            throw new InternshipException("THE STUDENT AND THE COMPANY ARE NOT CORRELATED",409);
        }
        checkDate(student,internship, dateUtils);


        List<String> requests = new ArrayList<>();
        requests.add("The service/product met my expectations:");
        requests.add("I found the experience user-friendly and intuitive");
        requests.add("The staff/team was helpful and professional.");
        requests.add("I would recommend this service/product to others");
        requests.add("I am satisfied with the overall quality.");
        for (int i = 0; i < 5; i++) {
            Form form = new Form();
            form.setFormType(FormType.S_FEEDBACK);
            form.setRequest(requests.get(i));
            form.setResponse(feedBackDTO.getFeedbacks().get(i));
            form.setStudent(student);
            form.setInternship(internship);
            formRepository.save(form);
        }

    }

    void checkDate(Student student, Internship internship, DateUtils dateUtils) {
        if (LocalDate.now().isBefore(dateUtils.getMidDate(internship.getStartDate(),internship.getEndDate()))) {
            throw new InternshipException("Feedback can not be inserted yet",409);
        }
        if (LocalDate.now().isAfter(
                dateUtils.getMidDate(internship.getStartDate(),internship.getEndDate()).plusWeeks(1)) &&
                LocalDate.now().isBefore(internship.getEndDate())) {
            throw new InternshipException("Feedback can not be inserted yet",409);
        }
        if(LocalDate.now().isAfter(internship.getEndDate().plusWeeks(1))){
            throw new InternshipException("Feedback can not be inserted anymore",409);
        }
        if(LocalDate.now().isBefore(dateUtils.getMidDate(internship.getStartDate(),internship.getEndDate()).plusWeeks(1))){
            if(formRepository.findByStudentAndInternshipAndFormType(student,internship.getCompany(),FormType.S_FEEDBACK).size() >= 5)
                throw new InternshipException("Feedback can not be inserted two times",409);
        }
        if(LocalDate.now().isBefore(internship.getEndDate().plusWeeks(1))){
            if(formRepository.findByStudentAndInternshipAndFormType(student,internship.getCompany(),FormType.S_FEEDBACK).size() >= 10)
                throw new InternshipException("Feedback can not be inserted two times",409);
        }
    }



        /*List<Form> formList = formRepository.findByInternshipAndCompanyAndFormType(internship, internship.getCompany(),FormType.FEEDBACK);
        List<FeedBackDTO> feedBackDTOList = new ArrayList<>();
        for(Form formIter : formList){
            feedBackDTOList.add(FormMapper.mapToFeedBackDTO(authEmail,formIter));
        }
        return feedBackDTOList;*/



    @Override
    public void handleReviewReceived(String authEmail, ReviewDTO reviewDTO) {
        Internship internship = internshipRepository.findInternshipByInternshipId(reviewDTO.getInternshipId()).orElseThrow(()->new RuntimeException("Internship not found"));
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));

        if(!formRepository.findByInternshipAndStudentAndFormType(internship,student, FormType.S_REVIEW).isEmpty()){
            throw new InternshipException("Student has already inserted a review",409);
        }

        if(!internship.getSelectedStudents().contains(student)){
            throw new InternshipException("THE STUDENT AND THE COMPANY ARE NOT CORRELATED",409);
        }
        if(LocalDate.now().isBefore(internship.getEndDate())){
            throw new InternshipException("Too soon to compile",418);
        }
        List<String> requests = new ArrayList<>();
        requests.add("How do you rate this experience?");
        requests.add("What are your suggestions?");
        for (int i = 0; i < 2; i++) {
            Form form = new Form();
            form.setFormType(FormType.S_REVIEW);
            form.setRequest(requests.get(i));
            form.setResponse(reviewDTO.getReview().get(i));
            form.setStudent(student);
            form.setInternship(internship);
            formRepository.save(form);
        }
    }

    @Override
    public List<FormCompleteDTO> getMyForms(String authEmail) {
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new InternshipException("Student not found",404));
        List<Form> forms = formRepository.findByStudent(student);
        List<FormCompleteDTO> formDTOs = new ArrayList<>();
        for(Form form : forms){
           formDTOs.add(FormMapper.mapToCompleteFormDTO(form));
        }
        return formDTOs;
    }

    /**
     * @param studentEmail
     * @return
     */
    @Override
    public ShortStudentDTO getPublicStudentData(String studentEmail) {
        Student student = studentRepository.findByEmail(studentEmail).orElseThrow(()->new InternshipException("Student not found",404));
        List<Form> forms = formRepository.findByStudentAndFormType(student,FormType.CV);
        List<String> cvResponses = new ArrayList<>();
        for(Form form : forms){
            cvResponses.add(form.getResponse());
        }
        List<Form> publicForms = formRepository.findByStudentAndFormType(student,FormType.C_REVIEW);
        List<FormCompleteDTO> formDTOs = new ArrayList<>();
        for(Form form : publicForms){
            formDTOs.add(FormMapper.mapToCompleteFormDTO(form));
        }
        return new ShortStudentDTO(student.getEmail(),student.getName(),student.getSurname(),student.getDescription(),cvResponses,formDTOs);
    }
}
