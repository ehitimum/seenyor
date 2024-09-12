package com.yunyan.project.authorization.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.*;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yunyan.project.authorization.dto.commons.ConnectPermissionsDTO;
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
    public ResponseEntity<RolesResponseDTO> createRoles(CreateRolesDTO rolesRequest){
        Roles role = null;
        try {
            role = Roles.builder()
            .name(rolesRequest.getName())
            .status(true)
            .created_At(LocalDateTime.now())
            .updated_At(LocalDateTime.now())
            .build();
            rolesRepository.save(role);
            return new ResponseEntity<>(mapToRolesResponse(role), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
