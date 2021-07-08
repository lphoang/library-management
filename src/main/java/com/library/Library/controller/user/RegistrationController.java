package com.library.Library.controller.user;

import com.library.Library.dto.responses.AuthenticateResponse;
import com.library.Library.dto.requests.LoginRequest;
import com.library.Library.dto.requests.RegistrationRequest;
import com.library.Library.dto.responses.RegistrationResponse;
import com.library.Library.repository.AppUserRepository;
import com.library.Library.service.EmailService;
import com.library.Library.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AppUserRepository appUserRepository;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        Set<ConstraintViolation<RegistrationRequest>> violations = validator.validate(request);
        Set<String> setErrMsg = new HashSet<>();
        for(ConstraintViolation<RegistrationRequest> violation : violations){
            setErrMsg.add(violation.getMessage());
        }
        String errMsg = String.join(", ", setErrMsg);
        if (errMsg.isEmpty()) {
            RegistrationResponse userResponse = registrationService.register(request);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errMsg);
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        registrationService.confirmToken(token);
        return new ResponseEntity<>("Account Verified Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse> login(@RequestBody LoginRequest request) {
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        Set<String> setErrMsg = new HashSet<>();
        if(violations.isEmpty()){
            //Exception for access denied
            boolean isExist = appUserRepository.findByEmail(request.getEmail()).isPresent();
            if(!isExist){
                setErrMsg.add("You are not registered");
            }else{
                //Exception for not verifying email
                boolean isVerified = appUserRepository.findByEmail(request.getEmail()).get().getEnabled();
                if(!isVerified){
                    setErrMsg.add("Verify your email to login");
                }
            }
        }
        else{
            for(ConstraintViolation<LoginRequest> violation : violations){
                setErrMsg.add(violation.getMessage());
            }
        }
        String errMsg = String.join(", ", setErrMsg);
        if (errMsg.isEmpty()) {
            return new ResponseEntity<>(registrationService.login(request), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errMsg);
    }
}
