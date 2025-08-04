package com.example.odgateway.adapter.out.persistence.jpa;

import com.example.odgateway.application.port.out.RoleStoragePort;
import com.example.odgateway.domain.model.Role;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RoleStorageAdapter implements RoleStoragePort {

    private final RoleRepository repository;
    @Override
    public List<Role> findAll() {
        return repository.findAll().stream()
            .map(RoleEntity::toDomain)
            .toList();
    }

    @Override
    public void register(Role role) {
        repository.save(RoleEntity.of(role));
    }
}
