package com.library.Library.dto.responses;

import com.library.Library.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private String verifiedToken;
    private UserResponse user;
}
