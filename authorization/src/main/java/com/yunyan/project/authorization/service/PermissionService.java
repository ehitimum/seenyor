package com.yunyan.project.authorization.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yunyan.project.authorization.dto.PermissionRequest;
import com.yunyan.project.authorization.dto.PermissionResponse;
import com.yunyan.project.authorization.model.Permission;
import com.yunyan.project.authorization.repository.PermissionRepository;
import com.yunyan.project.authorization.repository.ResourceRepository;
import com.yunyan.project.authorization.dto.Response;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {
    @Autowired
    private final PermissionRepository repository;
    private final ResourceRepository resourceRepository;
    public ResponseEntity<PermissionResponse> createPermission(PermissionRequest request) {
        Permission permission = null;
        try {
            //Resource resource = resourceRepository.findById(request.getResource_id()).orElseThrow(() -> new EntityNotFoundException("Resource not found"));
            permission = Permission.builder()
            .name(request.getName())
            .end_point(request.getEnd_point())
            .created_at(LocalDateTime.now())
            .updated_at(LocalDateTime.now())
            .build();
            repository.save(permission);
            return new ResponseEntity<>(mapToPermissionResponse(permission),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<PermissionResponse> updatePermission(int uuid, PermissionRequest request) {
        try {
            Optional<Permission> existingPermission = repository.findById(uuid);
            if (existingPermission.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
                Permission tergetPermission = existingPermission.get();
                tergetPermission.setName(request.getName());
                tergetPermission.setEnd_point(request.getEnd_point());
                tergetPermission.setUpdated_at(LocalDateTime.now());
                repository.save(tergetPermission);
                return new ResponseEntity<>(mapToPermissionResponse(tergetPermission), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);        }
       
        
            
        
    }
    public ResponseEntity<Response> deletePermission(int uuid) {
        try {
            Optional<Permission> existingPermission = repository.findById(uuid);
            if (existingPermission.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Permission targetPermission = existingPermission.get();
            targetPermission.set_deleted(true);
            repository.save(targetPermission);
            return new ResponseEntity<>(Response.builder().message("Delete Successful!").build(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.builder().message("Failed!").build(), HttpStatus.BAD_REQUEST);
        }
        

    }
    public List<PermissionResponse> getPermissions() {
        List<Permission> permissions = repository.findAll();
        if (permissions.isEmpty()){
            return Collections.emptyList();
        }
        return permissions.stream().map(this::mapToPermissionResponse).toList();
    }
    
    private PermissionResponse mapToPermissionResponse(Permission permission){
        return PermissionResponse.builder()
        .id(permission.getId())
        .name(permission.getName())
        .end_point(permission.getEnd_point())
        .created_at(permission.getCreated_at())
        .updated_at(permission.getUpdated_at())
        .build();
    }


    
}
