package com.library.Library.dto.requests;

import lombok.*;
//DTO = Data transfer object
@Getter
@EqualsAndHashCode
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
