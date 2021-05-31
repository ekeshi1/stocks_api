package com.pp.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pp.database.AbstractAuditingEntity;
import com.pp.user.role.RoleJpa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="\"user\"")
public class UserJpa extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(
            name="UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column(name = "username")
    private String email;

    @Column
    @JsonIgnore
    private String password;

    @Column(name="full_name")
    private String fullName;

    @Column
    private String phone;

    @Column
    private String address;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="user_role",
            joinColumns = {
            @JoinColumn(name="user_id")
            },
            inverseJoinColumns = {
            @JoinColumn(name="role_id")
            })
    private Set<RoleJpa> roles;

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<RoleJpa> getRoles() {
        return roles;
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
