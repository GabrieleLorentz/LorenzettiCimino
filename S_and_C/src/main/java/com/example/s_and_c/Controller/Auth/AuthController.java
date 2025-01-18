package com.example.s_and_c.Controller.Auth;

import com.example.s_and_c.DTO.AuthRequestDTO;
import com.example.s_and_c.DTO.RegisterRequestDTO;
import com.example.s_and_c.DTO.UserTokenDTO;
import com.example.s_and_c.Service.Impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;


    @PostMapping("/registerStudent")
    public ResponseEntity<UserTokenDTO> registerStudent(@RequestBody RegisterRequestDTO RegStudRequest) {
        return ResponseEntity.ok(authService.registerStudent(RegStudRequest));
    }
    @PostMapping("/registerCompany")
    public ResponseEntity<UserTokenDTO> registerCompany(@RequestBody RegisterRequestDTO RegCompanyRequest) {
        return ResponseEntity.ok(authService.registerCompany(RegCompanyRequest));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<UserTokenDTO> Authenticate(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(authService.authenticate(authRequestDTO));
    }
}
