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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {
    @Autowired
    private final PermissionRepository repository;
    public ResponseEntity<PermissionResponse> createPermission(PermissionRequest request) {
        Permission permission = null;
        try {
            permission = Permission.builder()
            .name(request.getName())
            .end_point(request.getEnd_point())
            .created_at(LocalDateTime.now())
            .resource(request.getResource_id())
            .build();
            repository.save(permission);
            
            return new ResponseEntity<>((PermissionResponse.builder()
                .msg("Successful")
                .name(permission.getName())
                .end_point(permission.getEnd_point())
                .created_at(permission.getCreated_at())
                .updated_at(permission.getUpdated_at())
                .resource_id(permission.getResource())
                .build()),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>((PermissionResponse.builder().msg("Failed").build()),HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<PermissionResponse> updatePermission(int uuid, PermissionRequest request) {
        Optional<Permission> existingPermission = repository.findById(uuid);
        if(existingPermission.isPresent())
        {
            Permission tergetPermission = existingPermission.get();
            tergetPermission.setName(request.getName());
            tergetPermission.setEnd_point(request.getEnd_point());
            tergetPermission.setUpdated_at(LocalDateTime.now());
            tergetPermission.setResource(request.getResource_id());
            repository.save(tergetPermission);
            return new ResponseEntity<>((PermissionResponse.builder()
                .msg("Update Successful")
                .name(tergetPermission.getName())
                .end_point(tergetPermission.getEnd_point())
                .updated_at(tergetPermission.getUpdated_at())
                .resource_id(tergetPermission.getResource())
                .build()),
            HttpStatus.ACCEPTED);
        }
        else
        {
            return new ResponseEntity<>((PermissionResponse.builder()
                .msg("Update Failed")
                .build()),
            HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<PermissionResponse> deletePermission(int uuid) {
        Optional<Permission> existingPermission = repository.findById(uuid);
        if(existingPermission.isPresent()){
            Permission targetPermission = existingPermission.get();
            targetPermission.set_deleted(true);
            repository.save(targetPermission);
            return new ResponseEntity<>((PermissionResponse.builder().msg("Deleted").build()),HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>((PermissionResponse.builder().msg("Failed to Delete").build()),HttpStatus.BAD_REQUEST);
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
        .uuid(permission.getUuid())
        .name(permission.getName())
        .end_point(permission.getEnd_point())
        .created_at(permission.getCreated_at())
        .updated_at(permission.getUpdated_at())
        .resource_id(permission.getResource())
        .build();
    }

}
