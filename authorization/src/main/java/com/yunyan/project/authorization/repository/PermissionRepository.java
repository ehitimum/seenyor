package com.yunyan.project.authorization.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yunyan.project.authorization.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer>{
    // @Query("select e from Permission e where e.resource=?")
    // List<Permission> findAllByID(int uuid);
    //@Override
    @Query("select e from Permission e where e.is_deleted=false ")
    List<Permission> findAllByPermissionId(Set<Integer> permissionIds);

    @Override
    @Query("select e from Permission e where e.is_deleted=false")
    List<Permission> findAll();

    boolean existsByName(String name);
}
