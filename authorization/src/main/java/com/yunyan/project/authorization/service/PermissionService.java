package com.yunyan.project.authorization.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yunyan.project.authorization.model.Permission;
import com.yunyan.project.authorization.repository.PermissionRepository;
import com.yunyan.project.authorization.repository.ResourceRepository;
import com.yunyan.project.authorization.dto.commons.ResponseDTO;
import com.yunyan.project.authorization.dto.permissions.CreatePermissionDTO;
import com.yunyan.project.authorization.dto.permissions.PermissionResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {
    @Autowired
    private final PermissionRepository repository;
    private final ResourceRepository resourceRepository;
    public ResponseEntity<PermissionResponseDTO> createPermission(CreatePermissionDTO request) {
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

    public ResponseEntity<PermissionResponseDTO> updatePermission(int uuid, CreatePermissionDTO request) {
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
    public ResponseEntity<ResponseDTO> deletePermission(int uuid) {
        try {
            Optional<Permission> existingPermission = repository.findById(uuid);
            if (existingPermission.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Permission targetPermission = existingPermission.get();
            targetPermission.set_deleted(true);
            repository.save(targetPermission);
            return new ResponseEntity<>(ResponseDTO.builder().message("Delete Successful!").build(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseDTO.builder().message("Failed!").build(), HttpStatus.BAD_REQUEST);
        }
        

    }
    public List<PermissionResponseDTO> getPermissions() {
        List<Permission> permissions = repository.findAll();
        if (permissions.isEmpty()){
            return Collections.emptyList();
        }
        return permissions.stream().map(this::mapToPermissionResponse).toList();
    }
    
    private PermissionResponseDTO mapToPermissionResponse(Permission permission){
        return PermissionResponseDTO.builder()
        .id(permission.getId())
        .name(permission.getName())
        .end_point(permission.getEnd_point())
        .created_at(permission.getCreated_at())
        .updated_at(permission.getUpdated_at())
        .build();
    }


    
}
