package com.yunyan.project.authorization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yunyan.project.authorization.dto.commons.ConnectPermissionsDTO;
import com.yunyan.project.authorization.dto.commons.ResponseDTO;
import com.yunyan.project.authorization.dto.roles.CreateRolesDTO;
import com.yunyan.project.authorization.dto.roles.RolesResponseDTO;
import com.yunyan.project.authorization.service.RolesService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolesController {
    
    @Autowired
    private final RolesService rolesService;

    @GetMapping("/hello/world")
    @ResponseStatus(HttpStatus.OK)
    public String helloworld(){
        return "Hello World"; 
    }

    @PostMapping
    public ResponseEntity<RolesResponseDTO> createRoles(@RequestBody @Validated CreateRolesDTO rolesRequest){
        ResponseEntity<RolesResponseDTO> response = rolesService.createRoles(rolesRequest);
        return response;
    }

    @GetMapping
    public List<RolesResponseDTO> getAllRoles(){
        return rolesService.getAllRoles();
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<RolesResponseDTO> getOneRole(@PathVariable int uuid){
        return rolesService.getOneRole(uuid);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<RolesResponseDTO> updateRole(@PathVariable int uuid, @RequestBody @Validated CreateRolesDTO updateRolesRequest){
        ResponseEntity<RolesResponseDTO> response = rolesService.updateRole(uuid, updateRolesRequest);
        return response;
    }

    @PutMapping("/delete/{uuid}")
    public ResponseEntity<ResponseDTO> soft_deleteRole(@PathVariable int uuid){
        ResponseEntity<ResponseDTO> response = rolesService.soft_deleteRole(uuid);
        return response;
    }
    @PostMapping("/{uuid}")
    public ResponseEntity<RolesResponseDTO> addPermission(@PathVariable int uuid, @RequestBody ConnectPermissionsDTO request){
        ResponseEntity<RolesResponseDTO> response = rolesService.addPermission(uuid, request);
        return response;
    }


}
