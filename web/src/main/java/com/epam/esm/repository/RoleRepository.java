package com.epam.esm.repository;

import com.epam.esm.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByNameIgnoreCase(String name);
}
