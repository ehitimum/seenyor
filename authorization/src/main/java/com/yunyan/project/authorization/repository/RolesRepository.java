package com.yunyan.project.authorization.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yunyan.project.authorization.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    
    Optional<Roles> findById(int uuid);
    
    @Override
    @Query("select e from Roles e where e.is_deleted=false")
    List<Roles> findAll();

    
}
