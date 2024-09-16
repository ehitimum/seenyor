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
import org.springframework.web.bind.annotation.RestController;

import com.yunyan.project.authorization.dto.commons.ResponseDTO;
import com.yunyan.project.authorization.dto.permissions.CreatePermissionDTO;
import com.yunyan.project.authorization.dto.permissions.PermissionResponseDTO;
import com.yunyan.project.authorization.service.PermissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {
    @Autowired
    private final PermissionService service;
    @PostMapping
    public ResponseEntity<?> createPermission(@RequestBody @Validated CreatePermissionDTO request){
        ResponseEntity<?> response = service.createPermission(request); 
        return response;
    }
    @GetMapping
    public ResponseEntity<ResponseDTO<List<PermissionResponseDTO>>> getPermissions(){
        return service.getPermissions();
    }
    @PutMapping("/{uuid}")
    public ResponseEntity<?> updatePermission(@PathVariable int uuid, @RequestBody @Validated CreatePermissionDTO request){
        ResponseEntity<?> response = service.updatePermission(uuid,request); 
        return response;
    }
    @PutMapping("/delete/{uuid}")
    public ResponseEntity<?> deletePermission(@PathVariable int uuid){
        ResponseEntity<?> response = service.deletePermission(uuid);
        return response;
    }


}
