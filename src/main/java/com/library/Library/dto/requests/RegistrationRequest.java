package com.library.Library.dto.requests;

import javax.validation.constraints.*;

import lombok.*;
//DTO = Data transfer object
@Getter
@EqualsAndHashCode
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotNull(message = "Firstname is required")
    private String firstName;
    @NotNull(message = "Lastname is required")
    private String lastName;

    @NotNull(message = "Age is required")
    @Min(value = 10, message="Age should not be less than 10")
    @Max(value = 100, message="Age should not be greater than 100")
    private Integer age;

    @NotNull(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 6, max = 32, message = "Password must be between 6 and 32 characters")
    private String password;
}
