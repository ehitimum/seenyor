package com.yunyan.project.authorization.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yunyan.project.authorization.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer>{
    // @Query("select e from Permission e where e.resource=?")
    // List<Permission> findAllByID(int uuid);
}
