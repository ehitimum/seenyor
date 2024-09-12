package com.yunyan.project.authorization.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yunyan.project.authorization.dto.commons.ResponseDTO;
import com.yunyan.project.authorization.dto.permissions.CreatePermissionDTO;
import com.yunyan.project.authorization.dto.permissions.PermissionResponseDTO;
import com.yunyan.project.authorization.dto.resources.ResourceResponseDTO;
import com.yunyan.project.authorization.model.Permission;
import com.yunyan.project.authorization.service.PermissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {
    @Autowired
    private final PermissionService service;
    @PostMapping
    public ResponseEntity<PermissionResponseDTO> createPermission(@RequestBody @Validated CreatePermissionDTO request){
        ResponseEntity<PermissionResponseDTO> response = service.createPermission(request); 
        return response;
    }
    @GetMapping
    public List<PermissionResponseDTO> getPermissions(){
        return service.getPermissions();
    }
    // @GetMapping("/{uuid}")
    // public ResponseEntity<List<Permission>> getPermissionsByResourceId(@PathVariable int uuid) {
    //     List<Permission> permissions = service.getPermissionsByResourceId(uuid);
    //     return ResponseEntity.ok(permissions);
    // }

    @PutMapping("/{uuid}")
    public ResponseEntity<PermissionResponseDTO> updatePermission(@PathVariable int uuid, @RequestBody @Validated CreatePermissionDTO request){
        ResponseEntity<PermissionResponseDTO> response = service.updatePermission(uuid,request); 
        return response;
    }
    @PutMapping("/delete/{uuid}")
    public ResponseEntity<ResponseDTO> deletePermission(@PathVariable int uuid){
        ResponseEntity<ResponseDTO> response = service.deletePermission(uuid);
        return response;
    }


}
