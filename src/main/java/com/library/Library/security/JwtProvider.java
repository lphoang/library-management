package com.library.Library.security;

import com.library.Library.constant.AppUserRole;
import com.library.Library.entity.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Service
public class JwtProvider {
    @Async
    public String generateToken(Authentication authentication, AppUserRole role) {
        AppUser principal = (AppUser) authentication.getPrincipal();
        String SECRET = "lephuochoang98tcv@gmail.com_and_this_is_my_password_==>";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(role.name());

        return Jwts.builder()
                .setSubject(principal.getEmail())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .signWith(SignatureAlgorithm.HS256,
                        SECRET.getBytes())
                .compact();
    }
}
