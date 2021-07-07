package com.library.Library.service;

import com.library.Library.dto.responses.AuthenticateResponse;
import com.library.Library.dto.requests.LoginRequest;
import com.library.Library.dto.requests.RegistrationRequest;
import com.library.Library.dto.responses.RegistrationResponse;
import com.library.Library.dto.responses.UserResponse;
import com.library.Library.entity.AppUser;
import com.library.Library.constant.AppUserRole;
import com.library.Library.repository.AppUserRepository;
import com.library.Library.security.JwtProvider;
import com.library.Library.service.impl.EmailSender;
import com.library.Library.entity.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.lang.String;
import java.util.Optional;

@Service
public class RegistrationService {

    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;
    private final EmailValidator emailValidator;

    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider = new JwtProvider();
    @Value("${email.baseUrl}")
    private final String baseUrl;

    @Autowired
    public RegistrationService(
            AppUserService appUserService,
            AppUserRepository appUserRepository,
            EmailValidator emailValidator,
            ConfirmationTokenService confirmationTokenService,
            EmailSender emailSender,
            AuthenticationManager authenticationManager,
            @Value("${email.baseUrl}") String baseUrl) {
        this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;
        this.emailValidator = emailValidator;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
        this.authenticationManager = authenticationManager;
        this.baseUrl = baseUrl;
    }

    public RegistrationResponse register(RegistrationRequest request) {
        //Exception for empty input
        if(request.getEmail() == null ) {
            throw new IllegalStateException("Email is required");
        }
        if(request.getPassword() == null ){
            throw new IllegalStateException("Password is required");
        }
        if(request.getFirstName() == null ) {
            throw new IllegalStateException("Firstname is required");
        }
        if(request.getLastName() == null ) {
            throw new IllegalStateException("Lastname is required");
        }
        if(request.getAge() == null ){
            throw new IllegalStateException("Age is required");
        }
        boolean isValidEmail = emailValidator
                .test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("Email is not valid");
        }

        RegistrationResponse userInfo = appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getAge(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
        String token = userInfo.getVerifiedToken();
        String link = baseUrl + "/user/register/confirm?token=" + token;
        System.out.println(link);
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getFirstName(), link));
        return userInfo;
    }

    @Transactional
    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email is already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail()
        );
    }

    public AuthenticateResponse login(LoginRequest request) {
        //Exception for empty input
        if(request.getEmail() == null ){
            throw new IllegalStateException("Email is required");
        }
        if(request.getPassword() == null ){
            throw new IllegalStateException("Password is required");
        }
        //Exception for access denied
        boolean isExist = appUserRepository.findByEmail(request.getEmail()).isPresent();
        if(!isExist){
            throw new IllegalStateException("Unauthorized");
        }else{
            //Exception for not verifying email
            boolean isVerified = appUserRepository.findByEmail(request.getEmail()).get().getEnabled();
            if(!isVerified){
                throw new IllegalStateException("Verify your email to login");
            }
        }

        Optional<AppUser> appUser = appUserRepository.findByEmail(request.getEmail());

        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String accessToken = jwtProvider.generateToken(auth, AppUserRole.USER);
        return new AuthenticateResponse(accessToken, new UserResponse(
                appUser.get().getId(),
                appUser.get().getFirstName(),
                appUser.get().getLastName(),
                appUser.get().getEmail(),
                appUser.get().getAge(),
                appUser.get().getPassword(),
                appUser.get().getAppUserRole(),
                appUser.get().getUsername(),
                appUser.get().getEnabled(),
                appUser.get().getLocked()
        ));
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
