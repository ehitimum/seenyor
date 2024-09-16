package com.yunyan.project.authorization.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yunyan.project.authorization.dto.commons.FieldErrorDTO;
import com.yunyan.project.authorization.dto.commons.ResponseDTO;
import com.yunyan.project.authorization.dto.permissions.CreatePermissionDTO;
import com.yunyan.project.authorization.dto.permissions.PermissionResponseDTO;
import com.yunyan.project.authorization.model.Permission;
import com.yunyan.project.authorization.repository.PermissionRepository;
import com.yunyan.project.authorization.repository.ResourceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {
    @Autowired
    private final PermissionRepository repository;
    private final ResourceRepository resourceRepository;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ResponseEntity<?> createPermission(CreatePermissionDTO request) {
        Permission permission = null;
        try {
            permission = Permission.builder()
            .name(request.getName())
            .end_point(request.getEnd_point())
            .created_at(LocalDateTime.now())
            .updated_at(LocalDateTime.now())
            .build();
            repository.save(permission);
            ResponseEntity<ResponseDTO<Permission>> response = buildResponse(permission, HttpStatus.CREATED.value(), "Successfully Permission created", true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (DataIntegrityViolationException e){
            FieldErrorDTO fieldError = new FieldErrorDTO("Database", List.of("A role with same value exists"));
            return buildErrorResponse(List.of(fieldError), "Duplicate Value Error", HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            FieldErrorDTO fieldError = new FieldErrorDTO("General", List.of("An unexpected error occurred."));
            return buildErrorResponse(List.of(fieldError), "An error occurred in roles", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updatePermission(int uuid, CreatePermissionDTO request) {
        try {
            Optional<Permission> existingPermission = repository.findById(uuid);
            boolean isDuplicate = repository.existsByName(request.getName());
            if (existingPermission.isEmpty()) {
                @SuppressWarnings({ "unchecked", "rawtypes" })
                FieldErrorDTO fieldError = new FieldErrorDTO("Not Found", List.of("The user with " + uuid + " is not found!"));
                return buildErrorResponse(List.of(fieldError), "User Not Found", HttpStatus.NOT_FOUND);
            }
            if(isDuplicate){
                throw new DataIntegrityViolationException("A Permission with the name '" + request.getName() + "' already exists.");
            }
            Permission tergetPermission = existingPermission.get();
            tergetPermission.setName(request.getName());
            tergetPermission.setEnd_point(request.getEnd_point());
            tergetPermission.setUpdated_at(LocalDateTime.now());
            repository.save(tergetPermission);
            ResponseEntity<ResponseDTO<Permission>> response = buildResponse(tergetPermission, HttpStatus.ACCEPTED.value(), "Successfully Permission Updated", true);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } 
        catch (Exception e) {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            FieldErrorDTO fieldError = new FieldErrorDTO("General", List.of("An unexpected error occurred."));
            return buildErrorResponse(List.of(fieldError), "An error occurred in roles", HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }
    public ResponseEntity<?> deletePermission(int uuid) {
        try {
            Optional<Permission> existingPermission = repository.findById(uuid);
            if (existingPermission.isEmpty()) {
                @SuppressWarnings({ "unchecked", "rawtypes" })
                FieldErrorDTO fieldError = new FieldErrorDTO("Not Found", List.of("The user with " + uuid + " is not found!"));
                return buildErrorResponse(List.of(fieldError), "User Not Found", HttpStatus.NOT_FOUND);
            }
            Permission targetPermission = existingPermission.get();
            targetPermission.set_deleted(true);
            repository.save(targetPermission);
            ResponseEntity<ResponseDTO<Permission>> response = buildResponse(targetPermission, HttpStatus.ACCEPTED.value(), "Successfully Permission Deleted", true);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            FieldErrorDTO fieldError = new FieldErrorDTO("General", List.of("An unexpected error occurred."));
            return buildErrorResponse(List.of(fieldError), "An error occurred in roles", HttpStatus.INTERNAL_SERVER_ERROR);
        }  
        

    }
    public ResponseEntity<ResponseDTO<List<PermissionResponseDTO>>> getPermissions() {
        List<Permission> permissions = repository.findAll();
        if (permissions.isEmpty()){
            return buildResponse(Collections.emptyList(), HttpStatus.NO_CONTENT.value(), "No roles found", false);
        }
        List<PermissionResponseDTO> responseDTOList = permissions.stream().map(this::mapToPermissionResponse).toList();
        return buildResponse(responseDTOList, HttpStatus.OK.value(), "Permissions fetched successfully", true);
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

    private <T> ResponseEntity<ResponseDTO<T>> buildResponse(T data, int statusCode, String message, boolean status) {
        ResponseDTO<T> response = new ResponseDTO<>();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setStatus(status);
        response.setData(data);
        return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode));
    }

    @SuppressWarnings("rawtypes")
    private ResponseEntity<ResponseDTO<List<FieldErrorDTO>>> buildErrorResponse(List<FieldErrorDTO> fieldErrors, String message, HttpStatus status) {
        ResponseDTO<List<FieldErrorDTO>> response = new ResponseDTO<>();
        response.setStatusCode(status.value());
        response.setMessage(message);
        response.setStatus(false);
        response.setData(fieldErrors);
        return new ResponseEntity<>(response, status);
    }


    
}
