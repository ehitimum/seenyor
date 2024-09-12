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
    public ResponseEntity<ResourceResponseDTO> createResource(@RequestBody @Validated CretaeResourceDTO request){
        ResponseEntity<ResourceResponseDTO> response = resourceService.createResource(request);
        return response; 
    }
    @GetMapping
    public List<ResourceResponseDTO> getResources(){
        return resourceService.getAllResource();
    }
    @PutMapping("/{uuid}")
    public ResponseEntity<ResourceResponseDTO> updateResource(@PathVariable int uuid, @RequestBody @Validated CretaeResourceDTO request){
        ResponseEntity<ResourceResponseDTO> response = resourceService.updateResource(uuid, request);
        return response;
    }
    @PutMapping("/delete/{uuid}")
    public ResponseEntity<ResponseDTO> deleteResource(@PathVariable int uuid){
        ResponseEntity<ResponseDTO> response = resourceService.deleteResource(uuid);
        return response;
    }
    @PostMapping("/{uuid}")
    public ResponseEntity<ResourceResponseDTO> addPermissionToResource(@PathVariable int uuid, @RequestBody ConnectPermissionsDTO request){
        ResponseEntity<ResourceResponseDTO> response = resourceService.addPermissionToResource(uuid, request);
        return response;
    }
    

}
