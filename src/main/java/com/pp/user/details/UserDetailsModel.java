package com.pp.user.details;

import com.pp.user.UserJpa;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class UserDetailsModel implements Serializable {
    private static final long serialVersionUID = 1L;

    UUID id;
    String email;
    String fullName;
    String phone;
    String address;

    public static UserDetailsModel newInstance(UserJpa user) {
        return new UserDetailsModel(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPhone(),
                user.getAddress()
        );
    }
}
