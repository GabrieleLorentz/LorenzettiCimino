package com.example.s_and_c;

import com.example.s_and_c.DTO.AuthDTOs.AuthRequestDTO;
import com.example.s_and_c.DTO.AuthDTOs.RegisterRequestDTO;
import com.example.s_and_c.DTO.AuthDTOs.UserTokenDTO;
import com.example.s_and_c.Entities.Status.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testRegisterStudent() throws Exception {
        List<String> cv = new ArrayList<>();
        cv.add("12345");
        cv.add("12345");
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO("prova@gmail.com","prova","prova","riprova","prova",null,cv);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(registerRequestDTO);
        UserTokenDTO userTokenDTO = new UserTokenDTO("prova@gmail.com", null, Role.STUDENT.toString());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/registerStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterCompany() throws Exception {
        Long vat = 41L;
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO("prova@gmail.com","prova","prova",null,"prova",vat,null);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(registerRequestDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/registerCompany")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest());



        RegisterRequestDTO registerRequestDTO2 = new RegisterRequestDTO("prova1@gmail.com","prova","prova",null,"prova",vat,null);
        jsonBody = objectMapper.writeValueAsString(registerRequestDTO2);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/registerCompany")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isConflict());

        RegisterRequestDTO registerRequestDTO3 = new RegisterRequestDTO("prova2@gmail.com","prova","prova",null,"prova",vat,null);
        jsonBody = objectMapper.writeValueAsString(registerRequestDTO2);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/registerCompany")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());
    }

    @Test
    public void login() throws Exception {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO("prova@gmail.com","prova1");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(authRequestDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isForbidden());

        authRequestDTO.setPassword("prova");
        jsonBody = objectMapper.writeValueAsString(authRequestDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());

    }
}
