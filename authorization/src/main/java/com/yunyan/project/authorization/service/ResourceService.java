package com.yunyan.project.authorization.service;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;

import com.yunyan.project.authorization.dto.ResourceRequest;
import com.yunyan.project.authorization.dto.Response;
import com.yunyan.project.authorization.model.Resource;

public class ResourceService {

    public ResponseEntity<Response> createResource(ResourceRequest request) {
        Resource resource = null;
        try {
            resource = Resource.builder()
            .name(request.getName())
            .end_points(request.getEnd_point())
            .created_at(LocalDateTime.now())
            .build();


        } catch (Exception e) {
        }
    }

}
