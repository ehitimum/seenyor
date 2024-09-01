package com.yunyan.project.authorization.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yunyan.project.authorization.dto.Response;
import com.yunyan.project.authorization.dto.RolesRequest;
import com.yunyan.project.authorization.dto.RolesResponse;
import com.yunyan.project.authorization.service.RolesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolesController {

    private final RolesService rolesService;


    @GetMapping("/hello/world")
    @ResponseStatus(HttpStatus.OK)
    public String helloworld(){
        return "Hello World"; 
    }

    @PostMapping("/create-role")
    public ResponseEntity<Response> createRoles(@RequestBody @Validated RolesRequest rolesRequest){
        ResponseEntity<Response> response = rolesService.createRoles(rolesRequest);
        return response;
    }

    @GetMapping
    public List<RolesResponse> getAllRoles(){
        return rolesService.getAllRoles();
    }

    @PutMapping("/update-role/{uuid}")
    public ResponseEntity<Response> updateRole(@PathVariable int uuid, @RequestBody @Validated RolesRequest updateRolesRequest){
        ResponseEntity<Response> response = rolesService.updateRole(uuid, updateRolesRequest);
        return response;
    }

    @PutMapping("/delete-role/{uuid}")
    public ResponseEntity<Response> soft_deleteRole(@PathVariable int uuid){
        ResponseEntity<Response> response = rolesService.soft_deleteRole(uuid);
        return response;
    }

}
