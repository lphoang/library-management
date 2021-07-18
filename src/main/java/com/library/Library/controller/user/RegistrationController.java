package com.library.Library.controller.user;

import com.library.Library.dto.responses.AuthenticateResponse;
import com.library.Library.dto.requests.LoginRequest;
import com.library.Library.dto.requests.RegistrationRequest;
import com.library.Library.dto.responses.RegistrationResponse;
import com.library.Library.entity.AppUser;
import com.library.Library.service.AppUserService;
import com.library.Library.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        registrationService.confirmToken(token);
        return new ResponseEntity<>("Account Verified Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse> login(@RequestBody LoginRequest request) {
        return registrationService.login(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AppUser>> getUserInfo(@PathVariable("id") String id){
        return appUserService.getUserInfo(id);
    }
}
