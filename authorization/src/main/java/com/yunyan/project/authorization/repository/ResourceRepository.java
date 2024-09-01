package com.yunyan.project.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yunyan.project.authorization.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

}
