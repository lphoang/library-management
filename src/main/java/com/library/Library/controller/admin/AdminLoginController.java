package com.library.Library.controller.admin;

import com.library.Library.dto.requests.LoginRequest;
import com.library.Library.dto.responses.AuthenticateResponse;
import com.library.Library.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin")
@AllArgsConstructor
public class AdminLoginController {

    private final RegistrationService registrationService;

    @PostMapping("/login")
    public AuthenticateResponse login(@RequestBody LoginRequest request){
        return registrationService.login(request);
    }
}
