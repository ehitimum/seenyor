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

import com.yunyan.project.authorization.dto.commons.ConnectPermissionsDTO;
import com.yunyan.project.authorization.dto.commons.ResponseDTO;
import com.yunyan.project.authorization.dto.resources.CretaeResourceDTO;
import com.yunyan.project.authorization.dto.resources.ResourceResponseDTO;
import com.yunyan.project.authorization.service.ResourceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceController {
    
    @Autowired
    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<?> createResource(@RequestBody @Validated CretaeResourceDTO request){
        ResponseEntity<?> response = resourceService.createResource(request);
        return response; 
    }
    @GetMapping
    public ResponseEntity<ResponseDTO<List<ResourceResponseDTO>>> getResources(){
        return resourceService.getAllResource();
    }
    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateResource(@PathVariable int uuid, @RequestBody @Validated CretaeResourceDTO request){
        ResponseEntity<?> response = resourceService.updateResource(uuid, request);
        return response;
    }
    @PutMapping("/delete/{uuid}")
    public ResponseEntity<?> deleteResource(@PathVariable int uuid){
        ResponseEntity<?> response = resourceService.deleteResource(uuid);
        return response;
    }
    @PostMapping("/{uuid}")
    public ResponseEntity<?> addPermissionToResource(@PathVariable int uuid, @RequestBody ConnectPermissionsDTO request){
        ResponseEntity<?> response = resourceService.addPermissionToResource(uuid, request);
        return response;
    }
    

}
