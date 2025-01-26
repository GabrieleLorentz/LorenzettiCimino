package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.DTO.AuthDTOs.AuthRequestDTO;
import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;
import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.InternshipDTOs.FormDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipForStudentsDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.UpdatedStudentDTO;
import com.example.s_and_c.DTO.AuthDTOs.UserTokenDTO;
import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Entities.Status.FormType;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Exception.ResourceNotFoundException;
import com.example.s_and_c.Mapper.FormMapper;
import com.example.s_and_c.Mapper.InternshipMapper;
import com.example.s_and_c.Mapper.StudentMapper;
import com.example.s_and_c.Repositories.FormRepository;
import com.example.s_and_c.Repositories.InternshipRepository;
import com.example.s_and_c.Repositories.StudentRepository;
import com.example.s_and_c.Service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final AuthService authService;
    private final FormRepository formRepository;
    private StudentRepository studentRepository;
    private InternshipRepository internshipRepository;
    private final PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public StudentDTO getStudent(String email) {
        Student student = studentRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Student with id " + email + " not found"));
        return StudentMapper.mapToStudentDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentMapper::mapToStudentDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UpdatedStudentDTO updateStudent(String email, @NotNull StudentDTO studentDTO) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + email + " not found"));

        try {
            if (!studentDTO.getEmail().equals(student.getEmail())) {
                Student newStudent = new Student();
                newStudent.setEmail(studentDTO.getEmail());
                newStudent.setName(studentDTO.getName());
                newStudent.setSurname(studentDTO.getSurname());
                newStudent.setDescription(studentDTO.getDescription());
                newStudent.setPassword(passwordEncoder.encode(studentDTO.getPassword()));

                entityManager.persist(newStudent);
                entityManager.flush();

                //internshipRepository.updateStudentInInternships(student.getEmail(),newStudent.getEmail());

                entityManager.remove(student);
                entityManager.flush();


                UserTokenDTO user = authService.authenticate(new AuthRequestDTO(newStudent.getEmail(), studentDTO.getPassword()));
                String token = user.getToken();
                return StudentMapper.mapToUpdatedStudentDTO(newStudent, token);
            }

            if (!studentDTO.getName().equals(student.getName())) {
                student.setName(studentDTO.getName());
            }
            if (!studentDTO.getSurname().equals(student.getSurname())) {
                student.setSurname(studentDTO.getSurname());
            }
            if (!studentDTO.getDescription().equals(student.getDescription())) {
                student.setDescription(studentDTO.getDescription());
            }
            String password = passwordEncoder.encode(studentDTO.getPassword());
            if (!student.getPassword().equals(password)) {
                student.setPassword(password);
                UserTokenDTO user = authService.authenticate(new AuthRequestDTO(student.getEmail(), password));
                String token = user.getToken();
                return StudentMapper.mapToUpdatedStudentDTO(studentRepository.save(student), token);
            }
            return StudentMapper.mapToUpdatedStudentDTO(studentRepository.save(student));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Inserted Data violate constraint");
        }
    }

    @Override
    public void deleteStudent(String email) {

        studentRepository.deleteStudentByEmail(email);
    }

    @Override
    public void requestInternship(long internshipId, String authEmail) {
        System.out.println(internshipId);
        Internship internship = internshipRepository.findById((int)internshipId).orElseThrow(()->new RuntimeException("Internship not found"));
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));
        internship.addAppliedStudent(student);
        internshipRepository.save(internship);
    }

    @Override
    public List<InternshipForStudentsDTO> getPersonalInternships(String authEmail) {
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));
        List<Internship> internships = internshipRepository.findByAppliedStudentsContainingIgnoreCase(student);
        List<Internship> internshipsAccepted = internshipRepository.findByAcceptedStudentsContainingIgnoreCase(student);
        List<InternshipForStudentsDTO> internshipDTOList = new ArrayList<>();
        for(Internship internship : internships){
            internshipDTOList.add(InternshipMapper.mapToInternshipForAppliedStudentsDTO(internship));
        }
        for(Internship internship : internshipsAccepted){
            internshipDTOList.add(InternshipMapper.maptoInternshipForStudentsDTO(internship));
        }
        return internshipDTOList;
    }
    /**
     * @param authEmail
     * @param internshipId
     * @return
     */
    @Override
    public List<ComplaintDTO> handleComplaintToSend(String authEmail, int internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElseThrow(()->new RuntimeException("Internship not found"));
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));
        if(!internship.getSelectedStudents().contains(student)){
            throw new DataIntegrityViolationException("Internship does not belong to this company");
        }
        Form form = new Form();
        form.setFormType(FormType.COMPLAINT);
        form.setRequest("Tell us your complaint:");
        form.setResponse(null);
        form.setStudent(student);
        form.setInternship(internship);
        formRepository.save(form);
        List<Form> formList = formRepository.findByInternshipAndStudentAndFormType(internship,student, FormType.COMPLAINT);
        List<ComplaintDTO> complaintDTOList = new ArrayList<>();
        for(Form formIter : formList){
            complaintDTOList.add(FormMapper.mapToComplaintDTO(formIter));
        }
        return complaintDTOList;
    }

    @Override
    public void handleComplaint(String authEmail, ComplaintDTO complaintDTO) {
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));
        Internship internship = internshipRepository.findById(complaintDTO.getInternship_id()).orElseThrow(()->new RuntimeException("Internship not found"));
        List<Form> formList = formRepository.findByInternshipAndStudentAndFormType(internship, student, FormType.COMPLAINT);
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
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));
        Internship internship = internshipRepository.findById(feedBackDTO.getInternship_id()).orElseThrow(()->new RuntimeException("Internship not found"));
        List<Form> formList = formRepository.findByInternshipAndStudentAndFormType(internship, student,FormType.FEEDBACK);
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
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));
        if(!internship.getSelectedStudents().contains(student)){
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
            form.setStudent(student);
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
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));
        if(!internship.getSelectedStudents().contains(student)){
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
        Internship internship = internshipRepository.findById(reviewDTO.getInternship_id()).orElseThrow(()->new RuntimeException("Internship not found"));
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));
        List<Form> formList = formRepository.findByInternshipAndStudentAndFormType(internship, student, FormType.REVIEW);
        for (Form form : formList) {
            for( FormDTO formDTO : reviewDTO.getReview()){
                if(formDTO.getFormId() == form.getFormId()){
                    form.setResponse(formDTO.getResponse());
                    formRepository.save(form);
                }
            }
        }

    }
/*private StudentDTO mapInternshipToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setEmail(student.getEmail());
        studentDTO.setName(student.getName());
        studentDTO.setSurname(student.getSurname());
        studentDTO.setDescription(student.getDescription());
        return studentDTO;
    }*/

}
