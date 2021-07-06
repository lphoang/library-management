package com.library.Library.controller;

import com.library.Library.dto.responses.AuthenticateResponse;
import com.library.Library.dto.requests.LoginRequest;
import com.library.Library.dto.requests.RegistrationRequest;
import com.library.Library.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<List<Object>> register(@RequestBody RegistrationRequest request){
        List<Object> userResponse = registrationService.register(request);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token){
        registrationService.confirmToken(token);
        return new ResponseEntity<>("Account Verified Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticateResponse login(@RequestBody LoginRequest request){
        return registrationService.login(request);
    }
}
