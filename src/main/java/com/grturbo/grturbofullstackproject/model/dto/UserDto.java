package com.grturbo.grturbofullstackproject.model.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private List<UserRoleDto> roles = new ArrayList<>();

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public UserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<UserRoleDto> getRoles() {
        return roles;
    }

    public UserDto setRoles(List<UserRoleDto> roles) {
        this.roles = roles;
        return this;
    }
}
