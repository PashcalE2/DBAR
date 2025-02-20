package com.main.auth.repositories;

import com.main.auth.model.Permission;
import com.main.auth.model.PermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, PermissionId> {
}