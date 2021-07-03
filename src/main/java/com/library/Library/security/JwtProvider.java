package com.library.Library.security;

import com.library.Library.entity.AppUser;
import com.library.Library.exception.LibraryException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {
    private KeyStore keyStore;

    @PostConstruct
    public void init(){
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new LibraryException("Exception occurred while loading keystore", e);
        }
    }

    public String generateToken(Authentication authentication){
        AppUser principal = (AppUser) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getEmail())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try{
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        }catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
            throw new LibraryException("Exception occurred while retrieving public key from keystore", e);
        }
    }
}
