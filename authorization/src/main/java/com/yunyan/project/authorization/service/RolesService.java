package com.yunyan.project.authorization.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yunyan.project.authorization.dto.Response;
import com.yunyan.project.authorization.dto.RolesRequest;
import com.yunyan.project.authorization.dto.RolesResponse;
import com.yunyan.project.authorization.model.Roles;
import com.yunyan.project.authorization.repository.RolesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolesService {

    private final RolesRepository rolesRepository;
    public ResponseEntity<Response> createRoles(RolesRequest rolesRequest){
        Roles role = null;
        try {
            role = Roles.builder()
            .name(rolesRequest.getName())
            .status(true)
            .created_At(LocalDateTime.now())
            .build();
            rolesRepository.save(role);
            return new ResponseEntity<>((Response.builder().message("Successful").build()), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>((Response.builder().message(e.getMessage()).build()), HttpStatus.BAD_REQUEST);
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
        .build();
    }

    public ResponseEntity<Response> updateRole(int uuid, RolesRequest updaRolesRequest) {
       Optional<Roles> existingRole = rolesRepository.findById(uuid);

       if (existingRole.isPresent())
       {
            Roles roleToUpdate = existingRole.get();
            roleToUpdate.setName(updaRolesRequest.getName());
            roleToUpdate.setUpdated_At(LocalDateTime.now());
            rolesRepository.save(roleToUpdate);
            return new ResponseEntity<>((Response.builder().message("Update Successful").build()), HttpStatus.ACCEPTED); 
       }
       else
       {
            return new ResponseEntity<>((Response.builder().message("Role with ID " + uuid + " not found").build()),HttpStatus.BAD_REQUEST);
       }
    }

    public ResponseEntity<Response> soft_deleteRole(int uuid) {
        Optional<Roles> targetRole = rolesRepository.findById(uuid);
        if (targetRole.isPresent())
        {
            Roles roleToRemove = targetRole.get();
            roleToRemove.set_deleted(true);
            rolesRepository.save(roleToRemove);
            return new ResponseEntity<>((Response.builder().message("Delete Successful").build()), HttpStatus.OK); 
        }
        else
        {
            return new ResponseEntity<>((Response.builder().message("Role with ID " + uuid + " not found").build()),HttpStatus.BAD_REQUEST);
        }
    }

 

}
