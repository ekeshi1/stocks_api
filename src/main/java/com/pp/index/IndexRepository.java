package com.pp.index;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

import java.util.UUID;

@Repository
public interface IndexRepository  extends CrudRepository<IndexJpa, UUID> {

    @Override
    Set<IndexJpa> findAll();

    IndexJpa findByName(String name);
}
