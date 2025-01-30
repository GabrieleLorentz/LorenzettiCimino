package com.example.s_and_c;


import com.example.s_and_c.Controller.Auth.AuthController;
import com.example.s_and_c.Controller.CompanyController;
import com.example.s_and_c.Controller.StudentController;
import com.example.s_and_c.DTO.AuthDTOs.RegisterRequestDTO;
import com.example.s_and_c.DTO.AuthDTOs.UserTokenDTO;
import com.example.s_and_c.DTO.CompanyDTOs.CompanyDTO;
import com.example.s_and_c.DTO.CompanyDTOs.UpdatedCompanyDTO;
import com.example.s_and_c.DTO.FormDTO.FormResponseDTO;
import com.example.s_and_c.DTO.InternshipDTOs.FormDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InsertInternshipDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipIdDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.UpdatedStudentDTO;
import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Entities.Status.FormType;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Mapper.FormMapper;
import com.example.s_and_c.Repositories.*;
import com.example.s_and_c.Utils.InternshipException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentAndCompanyDataControllerTest {

    @Autowired
    private MockMvc mockMvcC, mockMvcS;
    @Autowired
    private StudentController studentController;
    @Autowired
    private CompanyController companyController;
    private String studentToken;
    private String companyToken;
    private long internshipId;
    private List<FormDTO> formToCompileDTO = new ArrayList<>();

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
        qualificationRepository.deleteAll();
        formRepository.deleteAll();
        companyRepository.deleteAll();
        studentRepository.deleteAll();
        internshipRepository.deleteAll();
        setup();
    }

    public void setup() throws Exception {
        mockMvcC = MockMvcBuilders.standaloneSetup(companyController)
                .build();
        mockMvcS = MockMvcBuilders.standaloneSetup(studentController)
                .build();
        regAndLogin();
    }

    private void regAndLogin() throws Exception {
        List<String> cv = new ArrayList<>();
        cv.add("12345");
        cv.add("12345");
        RegisterRequestDTO registerRequestDTOs = new RegisterRequestDTO(
                "provas@gmail.com",
                "prova","prova",
                "riprova","prova",
                null,
                cv
        );
        ResponseEntity<?> registerResponseStudent = authController.registerStudent(registerRequestDTOs);
        UserTokenDTO userTokenDTO1 = (UserTokenDTO) registerResponseStudent.getBody();
        studentToken = userTokenDTO1.getToken();

        RegisterRequestDTO registerRequestDTOc = new RegisterRequestDTO(
                "provac@gmail.com",
                "prova","prova",
                null,"prova",
                4L,null
        );
        ResponseEntity<?> registerResponseCompany = authController.registerCompany(registerRequestDTOc);
        UserTokenDTO userTokenDTO2 = (UserTokenDTO) registerResponseCompany.getBody();
        companyToken = userTokenDTO2.getToken();
    }

    @Test
    @Order(1)
    void whenCompanyToken_thenSuccess() throws Exception {
        MvcResult result = mockMvcC.perform(get("/api/company/personalData")
                        .header("Authorization", "Bearer " + companyToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.surname").doesNotExist())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.vat_number").exists())
                .andExpect(jsonPath("$.password").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        CompanyDTO company = objectMapper.readValue(content, CompanyDTO.class);
        Assertions.assertEquals("provac@gmail.com", company.getEmail());
        Assertions.assertEquals("prova", company.getName());
        Assertions.assertEquals("prova",company.getDescription());
        Assertions.assertEquals(4L,company.getVat_number());


    }

    @Test
    @Order(2)
    void whenStudentToken_thenSuccess() throws Exception {
        MvcResult result = mockMvcS.perform(get("/api/student/personalData")
                        .header("Authorization", "Bearer " + studentToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.surname").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.vat_number").doesNotExist())
                .andExpect(jsonPath("$.password").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        StudentDTO student = objectMapper.readValue(content, StudentDTO.class);
        Assertions.assertEquals("provas@gmail.com", student.getEmail());
        Assertions.assertEquals("prova", student.getName());
        Assertions.assertEquals("prova",student.getDescription());
        Assertions.assertEquals("riprova",student.getSurname());
    }

    @Test
    @Order(3)
    void whenStudentToken_thenUnauthorized() throws Exception {
        mockMvcC.perform(get("/api/company/personalData")
                        .header("Authorization", "Bearer " + studentToken))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(4)
    void whenNoToken_thenUnauthorized() throws Exception {
        mockMvcC.perform(get("/api/company/personalData"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(5)
    void whenInvalidToken_thenUnauthorized() throws Exception {
        mockMvcC.perform(get("/api/company/personalData")
                        .header("Authorization", "Bearer invalid_token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(6)
    void whenCompanyUpdateData_thenConflict() throws Exception {
        UpdatedCompanyDTO updatedCompanyDTO = new UpdatedCompanyDTO(
                "Gianfranco",
                "provas@gmail.com",
                "Fumagalli",
                "Fumagalli",
                8L,
                companyToken
        );

        String content = objectMapper.writeValueAsString(updatedCompanyDTO);
        MvcResult result = mockMvcC.perform(post("/api/company/updateData")
                        .header("Authorization", "Bearer " + companyToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isConflict())
                .andReturn();
        content = result.getResponse().getContentAsString();

    }

    @Test
    @Order(7)
    void whenCompanyUpdateData_thenSuccess() throws Exception {
        UpdatedCompanyDTO updatedCompanyDTO = new UpdatedCompanyDTO(
                "Gianfranco",
                "prova01@gmail.com",
                "Fumagalli",
                "Fumagalli",
                8L,
                companyToken
        );

        String content = objectMapper.writeValueAsString(updatedCompanyDTO);
        MvcResult result = mockMvcC.perform(post("/api/company/updateData")
                        .header("Authorization", "Bearer " + companyToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.surname").doesNotExist())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.vat_number").exists())
                .andExpect(jsonPath("$.password").exists())
                .andReturn();
        content = result.getResponse().getContentAsString();
        UpdatedCompanyDTO companyDTO = objectMapper.readValue(content, UpdatedCompanyDTO.class);
        Assertions.assertEquals("prova01@gmail.com", companyDTO.getEmail());
        Assertions.assertEquals("Gianfranco", companyDTO.getName());
        Assertions.assertEquals("Fumagalli", companyDTO.getDescription());
        Assertions.assertEquals(8L,companyDTO.getVat_number());
        if(!companyDTO.getNewToken().equals(companyToken))
            companyToken = companyDTO.getNewToken();
    }

    @Test
    @Order(8)
    void whenStudentUpdateData_thenSuccess() throws Exception {
        List<String> cv = new ArrayList<>();
        cv.add("12345");
        cv.add("12345");

        UpdatedStudentDTO studentDTO = new UpdatedStudentDTO(
                "Gianfranco",
                "Fumagalli",
                "prova0@gmail.com",
                "Fumagalli",
                "provas",
                cv
        );

        String content = objectMapper.writeValueAsString(studentDTO);
        MvcResult result = mockMvcC.perform(post("/api/student/updateData")
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.surname").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.vat_number").doesNotExist())
                .andExpect(jsonPath("$.password").exists())
                .andExpect(jsonPath("$.forms").exists())
                .andReturn();
        content = result.getResponse().getContentAsString();
        System.out.println(content);
        UpdatedStudentDTO student = objectMapper.readValue(content, UpdatedStudentDTO.class);
        Assertions.assertEquals("prova0@gmail.com", student.getEmail());
        Assertions.assertEquals("Gianfranco", student.getName());
        Assertions.assertEquals("provas", student.getDescription());
        Assertions.assertEquals("Fumagalli",student.getSurname());
        if(!student.getNewToken().equals(studentToken))
            studentToken = student.getNewToken();
    }

    @Test
    @Order(9)
    void whenCompanyInsertInternship_thenSuccess() throws Exception {
        InsertInternshipDTO internshipDTO = new InsertInternshipDTO();
        internshipDTO.setName("prova");
        internshipDTO.setDescription("prova");
        internshipDTO.setStartDate("2025-02-28");
        internshipDTO.setEndDate("2025-04-26");
        internshipDTO.setEndFormCompilingDate("2025-02-05");
        internshipDTO.setEndSelectionAcceptanceDate("2025-02-16");
        List<String> qualifications = new ArrayList<>();
        qualifications.add("prova");
        qualifications.add("prova");
        internshipDTO.setQualificationRequired(qualifications);
        internshipDTO.setSalary(560);
        internshipDTO.setQuestions(qualifications);

        String content = objectMapper.writeValueAsString(internshipDTO);
        MvcResult result = mockMvcC.perform(post("/api/company/insertInternship")
                        .header("Authorization", "Bearer " + companyToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        content = result.getResponse().getContentAsString();
       /* content = result.getResponse().getContentAsString();


        mapper.registerModule(new JavaTimeModule());
        InternshipDTO internshipDTO2 = mapper.readValue(content, InternshipDTO.class);
        Assertions.assertEquals("prova", internshipDTO2.getName());
        Assertions.assertEquals(560, internshipDTO2.getSalary());
        Assertions.assertEquals("prova", internshipDTO2.getQualification_required().getFirst());

        System.out.println(content);*/
    }

    @Test
    @Order(10)
    void whenStudentRequestAnInternship_thenSuccess() throws Exception {
        InternshipIdDTO internshipIdDTO = new InternshipIdDTO();
        Internship internship = internshipRepository
                .findByCompany(companyRepository
                        .findByEmail("prova01@gmail.com").orElseThrow(()->new RuntimeException(
                                "Internship not found"
                        ))).getFirst();
        internshipIdDTO.setId(internship.getInternshipId());

        String content = objectMapper.writeValueAsString(internshipIdDTO);
        mockMvcS.perform(post("/api/student/requestInternship")
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
        internshipId = internshipIdDTO.getId();
    }

    @Test
    @Order(11)
    void whenStudentRequestInternship_thenUnauthorized() throws Exception {
        InternshipIdDTO internshipIdDTO = new InternshipIdDTO();
        Internship internship = internshipRepository
                .findByCompany(companyRepository
                        .findByEmail("prova01@gmail.com").orElseThrow(()->new RuntimeException(
                                "Internship not found"
                        ))).getFirst();
        internshipIdDTO.setId(internship.getInternshipId());

        String content = objectMapper.writeValueAsString(internshipIdDTO);
        mockMvcS.perform(post("/api/student/requestInternship")
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(12)
    void whenCompanyAcceptAppliedStudent_thenSuccess() throws Exception {

        mockMvcS.perform(post("/api/company//studentAccepted/{email}_{internshipId}","prova0@gmail.com",internshipId)
                        .header("Authorization", "Bearer " + companyToken))
                .andExpect(status().isOk());
    }

    @Test
    @Order(13)
    void whenCompanyAcceptAppliedStudent_thenConflict() throws Exception {

        mockMvcS.perform(post("/api/company/studentAccepted/{email}_{internshipId}","prova0@gmail.com",internshipId)
                        .header("Authorization", "Bearer " + companyToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(14)
    void whenStudentRetrieveInternshipsInfoAndSendFormResponses_thenSuccess() throws Exception {
        FormResponseDTO formResponseDTO = new FormResponseDTO();
        Student student = studentRepository.getStudentByEmail("prova0@gmail.com").orElseThrow(()->new InternshipException("Student not found",404));
        Internship internship = internshipRepository.findInternshipByInternshipId(internshipId).orElseThrow(()->new InternshipException("Internship not found",404));
        List<FormDTO> formDTOList = new ArrayList<>();
        List<Form> formList = formRepository.findByInternshipAndStudentAndFormType(internship,student, FormType.INTERVIEW);
        for (Form form : formList) {
            FormDTO dto = FormMapper.mapToFormDTO(form);
            dto.setResponse("a");
            formDTOList.add(dto);
        }
        FormResponseDTO formResponseDTOS = new FormResponseDTO(internshipId, formDTOList);
        String content = objectMapper.writeValueAsString(formResponseDTOS);
        mockMvcS.perform(post("/api/student/formResponses")
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk()).andReturn();
        formRepository.findByStudentAndFormType(student,FormType.INTERVIEW).forEach(form -> {
            for(FormDTO forms : formResponseDTOS.getFormToCompile()){
                if(Long.compare(forms.getFormId(),form.getFormId())==0){
                    Assertions.assertEquals(form.getResponse(),forms.getResponse());
                }
            }
        });
    }
}
