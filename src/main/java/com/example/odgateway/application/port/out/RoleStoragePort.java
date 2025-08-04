package com.example.odgateway.application.port.out;

import com.example.odgateway.domain.model.Role;
import java.util.List;

public interface RoleStoragePort {

    List<Role> findAll();

    void register(Role role);
}
