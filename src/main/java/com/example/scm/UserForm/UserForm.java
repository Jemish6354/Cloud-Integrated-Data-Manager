package com.example.scm.UserForm;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {

    @NotBlank(message = "Name field is required")
    @Size(min=3, max=20, message = "character limit is between 3 to 20 (both include)")
    private String name;

    @Email(message="Invalid Email Address")
    private String email;

    @NotBlank(message="Password is required")
    @Size(min=6, max=20, message = "character limit is between 6 to 20 (both include)")
    private String password;
    private String phoneNumber;

    @NotBlank(message="about is required")
    private String about;


}
