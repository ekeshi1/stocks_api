package com.pp.account;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<AccountJpa, UUID> {

    List<AccountJpa> findAccountsByUserId(UUID userId);


}
