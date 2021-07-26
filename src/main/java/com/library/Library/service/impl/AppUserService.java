package com.library.Library.service.impl;

import com.library.Library.dto.responses.RegistrationResponse;
import com.library.Library.dto.responses.UserResponse;
import com.library.Library.entity.AppUser;
import com.library.Library.entity.ConfirmationToken;
import com.library.Library.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "User with email %s is not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public RegistrationResponse signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("Email is already taken");
        }

        appUser.setPassword(bCryptPasswordEncoder
                .encode(appUser.getPassword()));

        appUserRepository.save(appUser);

        // TODO: SEND confirmation token --> DONE
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //TODO: SEND email
        return new RegistrationResponse(token, new UserResponse(
                appUser.getId(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getEmail(),
                appUser.getAge(),
                appUser.getPassword(),
                appUser.getAppUserRole(),
                appUser.getUsername(),
                appUser.getEnabled(),
                appUser.getLocked()
        ));
    }

    public void enableAppUser(String email) {
        appUserRepository.enableAppUser(email);
    }

    @Transactional
    public ResponseEntity<Optional<AppUser>> getUserInfo(String id){
        if(appUserRepository.existsById(id)){
            return new ResponseEntity<>(appUserRepository.findById(id), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no user found");
    }
}
