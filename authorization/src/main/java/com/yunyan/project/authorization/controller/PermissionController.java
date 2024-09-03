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

import com.yunyan.project.authorization.dto.PermissionRequest;
import com.yunyan.project.authorization.dto.PermissionResponse;
import com.yunyan.project.authorization.dto.ResourceResponse;
import com.yunyan.project.authorization.dto.Response;
import com.yunyan.project.authorization.dto.RolesResponse;
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
    public ResponseEntity<PermissionResponse> createPermission(@RequestBody @Validated PermissionRequest request){
        ResponseEntity<PermissionResponse> response = service.createPermission(request); 
        return response;
    }
    @GetMapping
    public List<PermissionResponse> getPermissions(){
        return service.getPermissions();
    }
    // @GetMapping("/{uuid}")
    // public ResponseEntity<List<Permission>> getPermissionsByResourceId(@PathVariable int uuid) {
    //     List<Permission> permissions = service.getPermissionsByResourceId(uuid);
    //     return ResponseEntity.ok(permissions);
    // }

    @PutMapping("/{uuid}")
    public ResponseEntity<PermissionResponse> updatePermission(@PathVariable int uuid, @RequestBody @Validated PermissionRequest request){
        ResponseEntity<PermissionResponse> response = service.updatePermission(uuid,request); 
        return response;
    }
    @PutMapping("/delete/{uuid}")
    public ResponseEntity<PermissionResponse> deletePermission(@PathVariable int uuid){
        ResponseEntity<PermissionResponse> response = service.deletePermission(uuid);
        return response;
    }


}
