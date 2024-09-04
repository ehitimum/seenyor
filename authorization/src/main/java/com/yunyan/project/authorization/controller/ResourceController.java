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

import com.yunyan.project.authorization.dto.ResourceRequest;
import com.yunyan.project.authorization.dto.ResourceResponse;
import com.yunyan.project.authorization.dto.Response;
import com.yunyan.project.authorization.service.ResourceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceController {
    @Autowired
    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<ResourceResponse> createResource(@RequestBody @Validated ResourceRequest request){
        ResponseEntity<ResourceResponse> response = resourceService.createResource(request);
        return response; 
    }
    @GetMapping
    public List<ResourceResponse> getResources(){
        return resourceService.getAllResource();
    }
    @PutMapping("/{uuid}")
    public ResponseEntity<ResourceResponse> updateResource(@PathVariable int uuid, @RequestBody @Validated ResourceRequest request){
        ResponseEntity<ResourceResponse> response = resourceService.updateResource(uuid, request);
        return response;
    }
    @PutMapping("/delete/{uuid}")
    public ResponseEntity<Response> deleteResource(@PathVariable int uuid){
        ResponseEntity<Response> response = resourceService.deleteResource(uuid);
        return response;
    }
    

}
