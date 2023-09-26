package com.grturbo.grturbofullstackproject.model.dto;

import com.grturbo.grturbofullstackproject.model.validations.FieldMatch;
import com.grturbo.grturbofullstackproject.model.validations.UniqueUserEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@FieldMatch(firstField = "password", secondField = "confirmPassword", message = "The passwords do not match")
public class UserEditDto {

    private Long id;

    @NotEmpty(message = "User email should be provided.")
    @Email(message = "User email should be valid.")
    @UniqueUserEmail(message = "User email should be unique.")
    private String email;

    @NotEmpty
    @NotNull
    @Size(min = 5)
    private String password;

    @NotEmpty
    @NotNull
    @Size(min = 5)
    private String confirmPassword;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;

    @NotEmpty
    private List<UserRoleDto> roles;

    public UserEditDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<UserRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleDto> roles) {
        this.roles = roles;
    }
}