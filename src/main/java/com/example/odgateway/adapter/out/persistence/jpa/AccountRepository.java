package com.example.odgateway.adapter.out.persistence.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    boolean existsByUsername(String username);

    @Query("SELECT a FROM AccountEntity a JOIN FETCH a.roles WHERE a.username = :username and a.status = true")
    Optional<AccountEntity> findByUsername(String username);
}

