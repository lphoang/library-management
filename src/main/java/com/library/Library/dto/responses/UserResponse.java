package com.library.Library.dto.responses;

import com.library.Library.constant.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String password;
    private AppUserRole role;
    private String username;
    private boolean enabled;
    private boolean locked;
}
