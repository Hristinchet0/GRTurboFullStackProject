package com.grturbo.grturbofullstackproject.model.dto;

import com.grturbo.grturbofullstackproject.model.validations.FieldMatch;
import com.grturbo.grturbofullstackproject.model.validations.UniqueUserEmail;
import com.grturbo.grturbofullstackproject.model.validations.UniqueUsername;

import javax.validation.constraints.*;

@FieldMatch(firstField = "password", secondField = "confirmPassword", message = "The passwords do not match")
public class UserRegisterDto {

    @NotEmpty(message = "User email should be provided.")
    @Email(message = "User email should be valid.")
    @UniqueUserEmail(message = "User email should be unique.")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5, max = 20, message = "Password length must be between 5 and 20 characters.")
    private String password;

    @NotBlank(message = "Confirm Password cannot be empty")
    @Size(min = 5, max = 20, message = "Password length must be between 5 and 20 characters.")
    private String confirmPassword;

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 2, max = 20, message = "Username length must be between 2 and 20 characters.")
    @UniqueUsername
    private String username;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;

    public UserRegisterDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
