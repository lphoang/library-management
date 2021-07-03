package com.library.Library.dto;

import lombok.*;
//DTO = Data transfer object
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}
