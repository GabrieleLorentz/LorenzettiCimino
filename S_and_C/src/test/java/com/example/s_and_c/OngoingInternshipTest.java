package com.example.s_and_c;

import com.example.s_and_c.Controller.Auth.AuthController;
import com.example.s_and_c.Controller.CompanyController;
import com.example.s_and_c.Controller.StudentController;
import com.example.s_and_c.DTO.AuthDTOs.RegisterRequestDTO;
import com.example.s_and_c.DTO.AuthDTOs.UserTokenDTO;

import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;

import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.FormDTO.FormCompleteDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;
import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import com.example.s_and_c.Entities.*;
import com.example.s_and_c.Entities.Status.FormType;
import com.example.s_and_c.Repositories.*;
import com.example.s_and_c.Utils.InternshipException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OngoingInternshipTest {
    @Autowired
    private MockMvc mockMvcC, mockMvcS;
    @Autowired
    private StudentController studentController;
    @Autowired
    private CompanyController companyController;
    private String studentToken;
    private String companyToken;
    private long internshipId1;
    private long internshipId2;
    @Autowired
    private AuthController authController;
    @Autowired
    private CompanyRepository companyRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InternshipRepository internshipRepository;
    @Autowired
    private FormRepository formRepository;
    @Autowired
    private QualificationRepository qualificationRepository;

    @BeforeAll
    public void cleanDatabase() throws Exception {
        formRepository.deleteAll();
        qualificationRepository.deleteAll();
        companyRepository.deleteAll();
        studentRepository.deleteAll();
        internshipRepository.deleteAll();
        mockMvcC = MockMvcBuilders.standaloneSetup(companyController)
                .build();
        mockMvcS = MockMvcBuilders.standaloneSetup(studentController)
                .build();
        objectMapper.registerModule(new JavaTimeModule());
        regAndLogin();
        setup();
        setup2();
    }


    public void regAndLogin() throws Exception {
        List<String> cv = new ArrayList<>();
        cv.add("12345");
        cv.add("12345");
        RegisterRequestDTO registerRequestDTOs = new RegisterRequestDTO(
                "provah@gmail.com",
                "prova", "prova",
                "riprova", "prova",
                null,
                cv
        );
        ResponseEntity<?> registerResponseStudent = authController.registerStudent(registerRequestDTOs);
        UserTokenDTO userTokenDTO1 = (UserTokenDTO) registerResponseStudent.getBody();
        if (userTokenDTO1 != null) {
            studentToken = userTokenDTO1.getToken();
        }

        RegisterRequestDTO registerRequestDTOc = new RegisterRequestDTO(
                "provam@gmail.com",
                "prova", "prova",
                null, "prova",
                4L, null
        );
        ResponseEntity<?> registerResponseCompany = authController.registerCompany(registerRequestDTOc);
        UserTokenDTO userTokenDTO2 = (UserTokenDTO) registerResponseCompany.getBody();
        assert userTokenDTO2 != null;
        companyToken = userTokenDTO2.getToken();
    }

    public void setup() throws Exception {
        Company company = companyRepository.findByEmail("provam@gmail.com")
                .orElseThrow(() -> new InternshipException("company not found", 404));

        Internship internship = new Internship();
        internship.setName("prova");
        internship.setDescription("prova");
        internship.setStartDate(LocalDate.of(2025, 1, 30));
        internship.setEndDate(LocalDate.of(2025, 2, 18));
        internship.setEndFormCompilingDate(LocalDate.of(2025, 1, 20));
        internship.setEndSelectionAcceptanceDate(LocalDate.of(2025, 1, 25));
        internship.setSalary(560);
        internship.setCompany(company);
        internshipRepository.save(internship);

        List<String> questions = new ArrayList<>();
        questions.add("prova");
        questions.add("prova");
        List<Form> forms = new ArrayList<>();

        for(String question : questions) {
            Form form = new Form();
            form.setFormType(FormType.INTERVIEW);
            form.setRequest(question);
            form.setInternship(internship);
            forms.add(form);
        }
        formRepository.saveAll(forms);

        List<String> qualifications = new ArrayList<>();
        qualifications.add("prova");
        qualifications.add("prova");
        List<Qualification> qualificationList = new ArrayList<>();
        for(String qualification : qualifications) {
            Qualification qualification1 = new Qualification();
            qualification1.setInternship(internship);
            qualification1.setQualificationName(qualification);
            qualificationList.add(qualification1);
        }
        qualificationRepository.saveAll(qualificationList);

        Student student = studentRepository.findByEmail("provah@gmail.com").orElseThrow(()->new RuntimeException("student not found"));

        internship.addSelectedStudent(student);
        internshipRepository.save(internship);

        internshipId1 = internship.getInternshipId();
    }

    public void setup2() throws Exception {
        Company company = companyRepository.findByEmail("provam@gmail.com")
                .orElseThrow(() -> new InternshipException("company not found", 404));

        Internship internship = new Internship();
        internship.setName("prova2");
        internship.setDescription("prova2");
        internship.setStartDate(LocalDate.of(2025, 1, 2));
        internship.setEndDate(LocalDate.of(2025, 1, 31));
        internship.setEndFormCompilingDate(LocalDate.of(2025, 1, 1));
        internship.setEndSelectionAcceptanceDate(LocalDate.of(2025, 1, 1));
        internship.setSalary(560);
        internship.setCompany(company);
        internshipRepository.save(internship);

        List<String> questions = new ArrayList<>();
        questions.add("prova");
        questions.add("prova");
        List<Form> forms = new ArrayList<>();

        for(String question : questions) {
            Form form = new Form();
            form.setFormType(FormType.INTERVIEW);
            form.setRequest(question);
            form.setInternship(internship);
            forms.add(form);
        }
        formRepository.saveAll(forms);

        List<String> qualifications = new ArrayList<>();
        qualifications.add("prova");
        qualifications.add("prova");
        List<Qualification> qualificationList = new ArrayList<>();
        for(String qualification : qualifications) {
            Qualification qualification1 = new Qualification();
            qualification1.setInternship(internship);
            qualification1.setQualificationName(qualification);
            qualificationList.add(qualification1);
        }
        qualificationRepository.saveAll(qualificationList);

        Student student = studentRepository.findByEmail("provah@gmail.com").orElseThrow(()->new RuntimeException("student not found"));

        internship.addSelectedStudent(student);
        internshipRepository.save(internship);

        internshipId2 = internship.getInternshipId();
    }



    @Transactional
    @Test
    @Order(1)
    void testCompanySendComplaint() throws Exception {

        Internship internship = internshipRepository.findInternshipByInternshipId(internshipId1).orElseThrow(()->new InternshipException("Internship not found",404));
        Student student = studentRepository.findByEmail("provah@gmail.com").orElseThrow(()->new InternshipException("student not found",404));
        ComplaintDTO complaintDTO = new ComplaintDTO(
                internship.getInternshipId(),
                "prova",
                student.getEmail()
        );

        String context = objectMapper.writeValueAsString(complaintDTO);
        mockMvcC.perform(post("/api/company/sendComplaints")
                        .header("Authorization", "Bearer " + companyToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(context))
                .andExpect(status().isOk());

        List<Form> complaint = formRepository.findByInternship(internship);

        Assertions.assertNotNull(complaint);
        for(Form form : complaint) {
            if(form.getFormType().equals(FormType.C_COMPLAINT)) {
                Assertions.assertEquals("prova", form.getResponse());}
        }
    }

    @Transactional
    @Test
    @Order(2)
    void testStudentSendComplaint() throws Exception {

        Internship internship = internshipRepository.findInternshipByInternshipId(internshipId1).orElseThrow(()->new InternshipException("Internship not found",404));
        Student student = studentRepository.findByEmail("provah@gmail.com").orElseThrow(()->new InternshipException("student not found",404));
        ComplaintDTO complaintDTO = new ComplaintDTO(
                internship.getInternshipId(),
                "prova Student",
                null
        );

        String context = objectMapper.writeValueAsString(complaintDTO);
        mockMvcC.perform(post("/api/student/sendComplaints")
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(context))
                .andExpect(status().isOk());

        List<Form> complaint = formRepository.findByInternshipAndStudentAndFormType(internship,student,FormType.S_COMPLAINT);

        Assertions.assertNotNull(complaint);
        for(Form form : complaint) {
            if(form.getFormType().equals(FormType.S_COMPLAINT)) {
                Assertions.assertEquals("prova Student", form.getResponse());}
        }
    }

    @Transactional
    @Test
    @Order(3)
    void testStudentSendReview2() throws Exception {

        Internship internship = internshipRepository.findInternshipByInternshipId(internshipId2).orElseThrow(()->new InternshipException("Internship not found",404));
        Student student = studentRepository.findByEmail("provah@gmail.com").orElseThrow(()->new InternshipException("student not found",404));
        List<String> reviews = new ArrayList<>();
        reviews.add("5");
        reviews.add("prova");
        ReviewDTO reviewDTO = new ReviewDTO(null,internshipId2,reviews);

        String context = objectMapper.writeValueAsString(reviewDTO);
        mockMvcC.perform(post("/api/student/sendReview")
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(context))
                .andExpect(status().isOk());

        List<Form> reviewsStud = formRepository.findByInternshipAndStudentAndFormType(internship,student,FormType.S_REVIEW);

        Assertions.assertNotNull(reviewsStud);
        for(Form form : reviewsStud) {
            Assertions.assertTrue(reviews.contains(form.getResponse()));
        }
    }

    @Transactional
    @Test
    @Order(4)
    void testCompanySendReview2() throws Exception {

        Internship internship = internshipRepository.findInternshipByInternshipId(internshipId2).orElseThrow(()->new InternshipException("Internship not found",404));
        Student student = studentRepository.findByEmail("provah@gmail.com").orElseThrow(()->new InternshipException("student not found",404));
        List<String> reviews = new ArrayList<>();
        reviews.add("5");
        reviews.add("prova Review Company");
        ReviewDTO reviewDTO = new ReviewDTO(student.getEmail(),internshipId2,reviews);

        String context = objectMapper.writeValueAsString(reviewDTO);
        mockMvcC.perform(post("/api/company/sendReview")
                        .header("Authorization", "Bearer " + companyToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(context))
                .andExpect(status().isOk());

        List<Form> reviewsStud = formRepository.findByInternshipAndStudentAndFormType(internship,student,FormType.C_REVIEW);

        Assertions.assertNotNull(reviewsStud);
        for(Form form : reviewsStud) {
            Assertions.assertTrue(reviews.contains(form.getResponse()));
            Assertions.assertEquals(form.getStudent().getEmail(), student.getEmail());
        }
    }
    @Transactional
    @Test
    @Order(5)
    void testStudentSendFeedBack2() throws Exception {

        Internship internship = internshipRepository.findInternshipByInternshipId(internshipId2).orElseThrow(()->new InternshipException("Internship not found",404));
        Student student = studentRepository.findByEmail("provah@gmail.com").orElseThrow(()->new InternshipException("student not found",404));
        List<String> feedbacks = new ArrayList<>();
        feedbacks.add("5");
        feedbacks.add("1");
        feedbacks.add("2");
        feedbacks.add("3");
        feedbacks.add("4");
        FeedBackDTO feedBackDTO = new FeedBackDTO(null,internshipId2,feedbacks);

        String context = objectMapper.writeValueAsString(feedBackDTO);
        mockMvcC.perform(post("/api/student/sendFeedback")
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(context))
                .andExpect(status().isOk());

        List<Form> feedStud = formRepository.findByInternshipAndStudentAndFormType(internship,student,FormType.S_FEEDBACK);

        Assertions.assertNotNull(feedStud);
        for(Form form : feedStud) {
            Assertions.assertTrue(feedbacks.contains(form.getResponse()));
            Assertions.assertEquals(form.getStudent().getEmail(), student.getEmail());
            Assertions.assertEquals(FormType.S_FEEDBACK, form.getFormType());
        }
    }

    @Transactional
    @Test
    @Order(6)
    void testCompanySendFeedback2() throws Exception {

        Internship internship = internshipRepository.findInternshipByInternshipId(internshipId2).orElseThrow(()->new InternshipException("Internship not found",404));
        Student student = studentRepository.findByEmail("provah@gmail.com").orElseThrow(()->new InternshipException("student not found",404));
        List<String> feedbacks = new ArrayList<>();
        feedbacks.add("5");
        feedbacks.add("1");
        feedbacks.add("2");
        feedbacks.add("3");
        feedbacks.add("4");
        FeedBackDTO feedBackDTO = new FeedBackDTO(student.getEmail(),internshipId2,feedbacks);

        String context = objectMapper.writeValueAsString(feedBackDTO);
        mockMvcC.perform(post("/api/company/sendFeedback")
                        .header("Authorization", "Bearer " + companyToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(context))
                .andExpect(status().isOk());

        List<Form> feedCompany = formRepository.findByInternshipAndStudentAndFormType(internship,student,FormType.C_FEEDBACK);

        Assertions.assertNotNull(feedCompany);
        for(Form form : feedCompany) {
            Assertions.assertTrue(feedbacks.contains(form.getResponse()));
            Assertions.assertEquals(form.getStudent().getEmail(), student.getEmail());
            Assertions.assertEquals(FormType.C_FEEDBACK, form.getFormType());
        }
    }

    @Transactional
    @Test
    @Order(7)
    void testStudentMyForms() throws Exception {
        Student student = studentRepository.findByEmail("provah@gmail.com").orElseThrow(()->new InternshipException("student not found",404));

        MvcResult result = mockMvcS.perform(get("/api/student/myForms")
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        List<FormCompleteDTO> formCompleteDTOList = objectMapper.readValue(content, new TypeReference<List<FormCompleteDTO>>() {
        });
        Assertions.assertNotNull(formCompleteDTOList);
        for(FormCompleteDTO form : formCompleteDTOList) {
            Assertions.assertEquals(student.getEmail(), form.getStudent().getEmail());
        }
    }

    @Transactional
    @Test
    @Order(8)
    void testCompanyMyForms() throws Exception {
        Company company = companyRepository.findByEmail("provam@gmail.com").orElseThrow(()->new InternshipException("company not found",404));
        MvcResult result = mockMvcS.perform(get("/api/company/myForms")
                        .header("Authorization", "Bearer " + companyToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        List<FormCompleteDTO> formCompleteDTOList = objectMapper.readValue(content, new TypeReference<List<FormCompleteDTO>>() {
        });
        Assertions.assertNotNull(formCompleteDTOList);
        for(FormCompleteDTO form : formCompleteDTOList) {
            Assertions.assertEquals(company.getEmail(), form.getInternship().getCompanyEmail());
        }
    }

    @Transactional
    @Test
    @Order(8)
    void testStudentPublicData() throws Exception {
        Company company = companyRepository.findByEmail("provam@gmail.com").orElseThrow(()->new InternshipException("company not found",404));
        Student student = studentRepository.findByEmail("provah@gmail.com").orElseThrow(()->new InternshipException("student not found",404));
        MvcResult result = mockMvcS.perform(get("/api/publicProfile/getDataFromStudent/provah@gmail.com")
                        .header("Authorization", "Bearer " + companyToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        ShortStudentDTO shortStudentDTO = objectMapper.readValue(content, ShortStudentDTO.class );
        Assertions.assertNotNull(shortStudentDTO);
        for(FormCompleteDTO form : shortStudentDTO.getForms()) {
            Assertions.assertEquals(company.getEmail(), form.getCompany().getEmail());
        }
    }
}
