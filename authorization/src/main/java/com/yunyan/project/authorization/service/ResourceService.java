package com.yunyan.project.authorization.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yunyan.project.authorization.dto.commons.ConnectPermissionsDTO;
import com.yunyan.project.authorization.dto.commons.FieldErrorDTO;
import com.yunyan.project.authorization.dto.commons.ResponseDTO;
import com.yunyan.project.authorization.dto.resources.CretaeResourceDTO;
import com.yunyan.project.authorization.dto.resources.ResourceResponseDTO;
import com.yunyan.project.authorization.model.Permission;
import com.yunyan.project.authorization.model.Resource;
import com.yunyan.project.authorization.repository.PermissionRepository;
import com.yunyan.project.authorization.repository.ResourceRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ResourceService {
    @Autowired
    private final ResourceRepository repository;
    private final PermissionRepository permissionRepository;
   
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ResponseEntity<?> createResource(CretaeResourceDTO request) {
        Resource resource = null;
        try {
            resource = Resource.builder()
            .name(request.getName())
            .end_points(request.getEnd_point())
            .created_at(LocalDateTime.now())
            .updated_at(LocalDateTime.now())
            .build();
            repository.save(resource);
            ResponseEntity<ResponseDTO<Resource>> response = buildResponse(resource, HttpStatus.CREATED.value(), "Successfully roles created", true);
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

    public ResponseEntity<ResponseDTO<List<ResourceResponseDTO>>> getAllResource(){
        List<Resource> resource = repository.findAll();
        if(resource.isEmpty()){
            return buildResponse(Collections.emptyList(), HttpStatus.NO_CONTENT.value(), "No roles found", false);
        }
        List<ResourceResponseDTO> responseDTOList = resource.stream().map(this::mapToResourceResponse).toList();
        return buildResponse(responseDTOList, HttpStatus.OK.value(), "Resource fetched successfully", true);
    }
    private ResourceResponseDTO mapToResourceResponse(Resource resource){
        return ResourceResponseDTO.builder()
        .id(resource.getId())
        .name(resource.getName())
        .end_point(resource.getEnd_points())
        .created_at(resource.getCreated_at())
        .updated_at(resource.getUpdated_at())
        .permission_id(resource.getPermissions())
        .build();
    }

    public ResponseEntity<?> updateResource(int uuid, CretaeResourceDTO request) {
        try {
            Optional<Resource> existingResource = repository.findById(uuid);
            boolean isDuplicate = repository.existsByName(request.getName());
            if (existingResource.isEmpty()) {
                @SuppressWarnings({ "unchecked", "rawtypes" })
                FieldErrorDTO fieldError = new FieldErrorDTO("Not Found", List.of("The user with " + uuid + " is not found!"));
                return buildErrorResponse(List.of(fieldError), "User Not Found", HttpStatus.NOT_FOUND);
            }
            if(isDuplicate){
                throw new DataIntegrityViolationException("A Resource with the name '" + request.getName() + "' already exists.");
            }
            Resource targetResource = existingResource.get();
            targetResource.setName(request.getName());
            targetResource.setEnd_points(request.getEnd_point());
            targetResource.setUpdated_at(LocalDateTime.now());
            repository.save(targetResource);
            ResponseEntity<ResponseDTO<Resource>> response = buildResponse(targetResource, HttpStatus.CREATED.value(), "Successfully Resource created", true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            FieldErrorDTO fieldError = new FieldErrorDTO("General", List.of("An unexpected error occurred."));
            return buildErrorResponse(List.of(fieldError), "An error occurred in roles", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
            
            

    }
    public ResponseEntity<?> deleteResource(int uuid) {
        try {
            Optional<Resource> existingResource = repository.findById(uuid);
            if (existingResource.isEmpty()) {
                @SuppressWarnings({ "unchecked", "rawtypes" })
                FieldErrorDTO fieldError = new FieldErrorDTO("Not Found", List.of("The user with " + uuid + " is not found!"));
                return buildErrorResponse(List.of(fieldError), "User Not Found", HttpStatus.NOT_FOUND);
            }
            Resource targetResource = existingResource.get();
            targetResource.set_deleted(true);
            repository.save(targetResource);
            ResponseEntity<ResponseDTO<Resource>> response = buildResponse(targetResource, HttpStatus.CREATED.value(), "Successfully roles created", true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } 
        catch (Exception e) {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            FieldErrorDTO fieldError = new FieldErrorDTO("General", List.of("An unexpected error occurred."));
            return buildErrorResponse(List.of(fieldError), "An error occurred in roles", HttpStatus.INTERNAL_SERVER_ERROR);
        }  
        
    }
    public ResponseEntity<?> addPermissionToResource(int uuid, ConnectPermissionsDTO request) {
        try {
            Resource resource = repository.findById(uuid).orElseThrow(()->new ResourceNotFoundException("Resource Id"+uuid+" not found"));
            Set<Integer> permissionIds = request.getPermission_ids().stream().mapToInt(id -> id).boxed().collect(Collectors.toSet());
            List<Permission> permissions = permissionRepository.findAllById(permissionIds);
            resource.getPermissions().addAll(permissions);
            repository.save(resource);
            ResponseEntity<ResponseDTO<Resource>> response = buildResponse(resource, HttpStatus.CREATED.value(), "Successfully roles created", true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } 
        catch (Exception e) {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            FieldErrorDTO fieldError = new FieldErrorDTO("General", List.of("An unexpected error occurred."));
            return buildErrorResponse(List.of(fieldError), "An error occurred in roles", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
