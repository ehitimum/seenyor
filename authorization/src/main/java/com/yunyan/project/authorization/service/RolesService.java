package com.yunyan.project.authorization.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yunyan.project.authorization.dto.AddPermissionsToRole;
import com.yunyan.project.authorization.dto.Response;
import com.yunyan.project.authorization.dto.RolesRequest;
import com.yunyan.project.authorization.dto.RolesResponse;
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
    public ResponseEntity<RolesResponse> createRoles(RolesRequest rolesRequest){
        Roles role = null;
        try {
            role = Roles.builder()
            .name(rolesRequest.getName())
            .status(true)
            .created_At(LocalDateTime.now())
            .build();
            rolesRepository.save(role);
            return new ResponseEntity<>(mapToRolesResponse(role), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public List<RolesResponse> getAllRoles(){
        List<Roles> roles = rolesRepository.findAll();
        if (roles.isEmpty()){
            return Collections.emptyList();
        }
        return roles.stream().map(this::mapToRolesResponse).toList();
    }
    
    private RolesResponse mapToRolesResponse(Roles role){
        return RolesResponse.builder()
            .uuid(role.getUuid())
            .name(role.getName())
            .status(role.isStatus())
            .created_At(role.getCreated_At())
            .updated_At(role.getUpdated_At())
            .permission_id(role.getPermissions())
            .build();
    }

    public ResponseEntity<RolesResponse> updateRole(int uuid, RolesRequest updaRolesRequest) {
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

    public ResponseEntity<Response> soft_deleteRole(int uuid) {
        try {
            Optional<Roles> targetRole = rolesRepository.findById(uuid);
            if (targetRole.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Roles roleToRemove = targetRole.get();
            roleToRemove.set_deleted(true);
            rolesRepository.save(roleToRemove);
            return new ResponseEntity<>((Response.builder().message("Delete Successful").build()), HttpStatus.OK);        
        }
        catch (Exception e) {
            return new ResponseEntity<>((Response.builder().message("Role with ID not found").build()),HttpStatus.BAD_REQUEST);
        } 
    }

    public ResponseEntity<RolesResponse> addPermission(int uuid, AddPermissionsToRole request) {
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

    public ResponseEntity<RolesResponse> getOneRole(int uuid) {
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
