package com.pp.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface UserRepository extends CrudRepository<UserJpa, UUID> {
    UserJpa findByEmail(String username);

    @Query("select u from UserJpa u " +
            "join u.roles r " +
            "where r.name = (:role)")
    List<UserJpa> findByRole(String role);
}

