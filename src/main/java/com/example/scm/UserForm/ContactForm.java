package com.example.scm.UserForm;

import org.springframework.web.multipart.MultipartFile;

import com.example.scm.Validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter 
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid Email Address [example@gamil.com]")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phoneNumber;
    private String address;
    private String description;
    private boolean favourite;

    private String websiteLink;
    private String linkedinLink;

    // annotation create karenge jo file validate  (size, resolution)
    @ValidFile(message ="Invalid file")
    private MultipartFile contactImage; //(why? because private "String" picture;  )

    private String picture;


}
