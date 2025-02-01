package com.example.s_and_c;

import com.example.s_and_c.Controller.Auth.AuthController;
import com.example.s_and_c.Controller.CompanyController;
import com.example.s_and_c.Controller.StudentController;
import com.example.s_and_c.DTO.AuthDTOs.RegisterRequestDTO;
import com.example.s_and_c.DTO.AuthDTOs.UserTokenDTO;

import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;

import com.example.s_and_c.Entities.*;
import com.example.s_and_c.Entities.Status.FormType;
import com.example.s_and_c.Repositories.*;
import com.example.s_and_c.Utils.InternshipException;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private long internshipId;
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

    @Transactional
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

    @Transactional
    public void setup() throws Exception {
        Company company = companyRepository.findByEmail("provam@gmail.com")
                .orElseThrow(() -> new InternshipException("company not found", 404));

        Internship internship = new Internship();
        internship.setName("prova");
        internship.setDescription("prova");
        internship.setStartDate(LocalDate.of(2025, 1, 30));
        internship.setEndDate(LocalDate.of(2025, 2, 1));
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
        }
        qualificationRepository.saveAll(qualificationList);

        Student student = studentRepository.findByEmail("provah@gmail.com").orElseThrow(()->new RuntimeException("student not found"));

        internship.addSelectedStudent(student);
        internshipRepository.save(internship);

        internshipId = internship.getInternshipId();
    }



    @Test
    @Order(1)
    void testSendComplaint() throws Exception {

        Internship internship = internshipRepository.findInternshipByInternshipId(internshipId).orElseThrow(()->new InternshipException("Internship not found",404));
        Student student = studentRepository.findByEmail("provah@gmail.com").orElseThrow(()->new InternshipException("student not found",404));
        ComplaintDTO complaintDTO = new ComplaintDTO(
                internship.getInternshipId(),
                "prova",
                student.getEmail()
        );

        objectMapper.writeValueAsString(complaintDTO);
        mockMvcC.perform(post("/api/company/sendComplaints")
                        .header("Authorization", "Bearer " + companyToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        Form complaint = formRepository.findByInternship(internship);

        Assertions.assertNotNull(complaint);
        Assertions.assertEquals("prova", complaint.getResponse());
    }
}
