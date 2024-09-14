package com.yunyan.project.authorization.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yunyan.project.authorization.dto.commons.ConnectPermissionsDTO;
import com.yunyan.project.authorization.dto.commons.FieldErrorDTO;
import com.yunyan.project.authorization.dto.commons.ResponseDTO;
import com.yunyan.project.authorization.dto.roles.CreateRolesDTO;
import com.yunyan.project.authorization.dto.roles.RolesResponseDTO;
import com.yunyan.project.authorization.model.Permission;
import com.yunyan.project.authorization.model.Roles;
import com.yunyan.project.authorization.repository.PermissionRepository;
import com.yunyan.project.authorization.repository.RolesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolesService {
    @Autowired
    private final RolesRepository rolesRepository;
    private final PermissionRepository permissionRepository;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ResponseEntity<?> createRoles(CreateRolesDTO rolesRequest){
        Roles role = null;
        try {
            role = Roles.builder()
            .name(rolesRequest.getName())
            .status(true)
            .created_At(LocalDateTime.now())
            .updated_At(LocalDateTime.now())
            .build();
            rolesRepository.save(role);
            ResponseDTO<Roles> response = buildResponse(role, HttpStatus.CREATED.value(), "Successfully roles created", true);
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

    private <T> ResponseDTO<T> buildResponse(T data, int statusCode, String message, boolean status) {
        ResponseDTO<T> response = new ResponseDTO<>();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setStatus(status);
        response.setData(data);
        return response;
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
    
    public List<RolesResponseDTO> getAllRoles(){
        try {
            List<Roles> roles = rolesRepository.findAll();
            if (roles.isEmpty()){
                return Collections.emptyList();
            }
            return roles.stream().map(this::mapToRolesResponse).toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
       
    }
    
    // public ResponseEntity<?> getAllRoles(){
    //     try {
    //         List<Roles> roles = rolesRepository.findAll();
    //         if (roles.isEmpty()){
    //            ResponseDTO<List<Roles>> response = buildResponse(Collections.emptyList(), HttpStatus.NOT_FOUND.value(), "No roles added yet", false);
    //            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    //         }
    //         ResponseDTO<List<RolesResponseDTO>> response = buildResponse( roles.stream().map(this::mapToRolesResponse).toList(), HttpStatus.ACCEPTED.value(), "List of currently active Roles", true);
    //         return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    //     } catch (Exception e) {
    //         @SuppressWarnings({ "rawtypes", "unchecked" })
    //         FieldErrorDTO fieldError = new FieldErrorDTO("General", List.of("An unexpected error occurred."));
    //         return buildErrorResponse(List.of(fieldError), "An error occurred in roles", HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
       
    // }
    private RolesResponseDTO mapToRolesResponse(Roles role){
        return RolesResponseDTO.builder()
            .id(role.getId())
            .name(role.getName())
            .status(role.isStatus())
            .created_At(role.getCreated_At())
            .updated_At(role.getUpdated_At())
            .permission_id(role.getPermissions())
            .build();
    }

    public ResponseEntity<RolesResponseDTO> updateRole(int uuid, CreateRolesDTO updaRolesRequest) {
        try {
            Optional<Roles> existingRole = rolesRepository.findById(uuid);
            if (existingRole.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Roles roleToUpdate = existingRole.get();
            roleToUpdate.setName(updaRolesRequest.getName());
            roleToUpdate.setUpdated_At(LocalDateTime.now());
            rolesRepository.save(roleToUpdate);
            return new ResponseEntity<>(mapToRolesResponse(roleToUpdate), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseDTO> soft_deleteRole(int uuid) {
        try {
            Optional<Roles> targetRole = rolesRepository.findById(uuid);
            if (targetRole.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Roles roleToRemove = targetRole.get();
            roleToRemove.set_deleted(true);
            roleToRemove.setStatus(false);
            rolesRepository.save(roleToRemove);
            return new ResponseEntity<>((ResponseDTO.builder().message("Delete Successful").build()), HttpStatus.OK);        
        }
        catch (Exception e) {
            return new ResponseEntity<>((ResponseDTO.builder().message("Role with ID not found").build()),HttpStatus.BAD_REQUEST);
        } 
    }

    public ResponseEntity<RolesResponseDTO> addPermission(int uuid, ConnectPermissionsDTO request) {
        try {
            Optional<Roles> roleOptional = rolesRepository.findById(uuid);
            if (roleOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Roles role = roleOptional.get();
            Set<Integer> permissionIds = request.getPermission_ids().stream().mapToInt(id -> id).boxed().collect(Collectors.toSet());
            List<Permission> permissions = permissionRepository.findAllById(permissionIds);
            role.getPermissions().addAll(permissions);
            rolesRepository.save(role);
            return new ResponseEntity<>(mapToRolesResponse(role), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        

    }

    public ResponseEntity<RolesResponseDTO> getOneRole(int uuid) {
        try {
            Optional<Roles> roles = rolesRepository.findById(uuid);
            if (roles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Roles role = roles.get();
            return new ResponseEntity<>(mapToRolesResponse(role),
                HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

}
