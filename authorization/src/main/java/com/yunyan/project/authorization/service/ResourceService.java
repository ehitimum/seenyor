package com.yunyan.project.authorization.service;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yunyan.project.authorization.dto.ResourceRequest;
import com.yunyan.project.authorization.dto.Response;
import com.yunyan.project.authorization.model.Resource;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ResourceService {

    public ResponseEntity<Response> createResource(ResourceRequest request) {
        Resource resource = null;
        try {
            resource = Resource.builder()
            .name(request.getName())
            .end_points(request.getEnd_point())
            .created_at(LocalDateTime.now())
            .build();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }

}
