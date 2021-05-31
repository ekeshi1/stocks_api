package com.pp.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pp.user.role.Role;
import lombok.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
public class UserModel implements Serializable {

    private String email;
    private String password;
    private String fullName;

    private String phone;
    private String address;

    @JsonIgnore
    private Set<Role> roles;

    public UserModel(){
        this.roles = new HashSet<>(Arrays.asList(Role.USER));
    }
    public String getPassword(){
    return password;
    }
    public Set<Role> getRoles(){
        return roles;
    }

    public String getEmail() {
        return  email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
