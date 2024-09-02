package com.yunyan.project.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yunyan.project.authorization.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer>{

}
