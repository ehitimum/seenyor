package com.yunyan.project.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yunyan.project.authorization.dto.ResourceRequest;
import com.yunyan.project.authorization.dto.Response;
import com.yunyan.project.authorization.service.ResourceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceController {
    @Autowired
    private final ResourceService resourceService;

    @PostMapping("/create-resource")
    public ResponseEntity<Response> createResource(@RequestBody @Validated ResourceRequest request){
        ResponseEntity<Response> response = resourceService.createResource(request);
        return response; 
    }
}
