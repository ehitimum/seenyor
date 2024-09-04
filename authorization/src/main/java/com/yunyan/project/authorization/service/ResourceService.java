package com.yunyan.project.authorization.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yunyan.project.authorization.dto.ResourceRequest;
import com.yunyan.project.authorization.dto.ResourceResponse;
import com.yunyan.project.authorization.dto.Response;
import com.yunyan.project.authorization.model.Permission;
import com.yunyan.project.authorization.model.Resource;
import com.yunyan.project.authorization.repository.PermissionRepository;
import com.yunyan.project.authorization.repository.ResourceRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ResourceService {
    @Autowired
    private final ResourceRepository repository;
    private final PermissionRepository permissionRepository;

    public ResponseEntity<ResourceResponse> createResource(ResourceRequest request) {
        Resource resource = null;
        try {
            resource = Resource.builder()
            .name(request.getName())
            .end_points(request.getEnd_point())
            .created_at(LocalDateTime.now())
            .updated_at(LocalDateTime.now())
            .build();
            repository.save(resource);
            return new ResponseEntity<>(mapToResourceResponse(resource), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }
    public List<ResourceResponse> getAllResource(){
        List<Resource> resource = repository.findAll();
        if(resource.isEmpty()){
            return Collections.emptyList();
        }
        return resource.stream().map(this::mapToResourceResponse).toList();
    }
    private ResourceResponse mapToResourceResponse(Resource resource){
        return ResourceResponse.builder()
        .uuid(resource.getUuid())
        .name(resource.getName())
        .end_point(resource.getEnd_points())
        .created_at(resource.getCreated_at())
        .updated_at(resource.getUpdated_at())
        .build();
    }
    public ResponseEntity<ResourceResponse> updateResource(int uuid, ResourceRequest request) {
        try {
            Optional<Resource> existingResource = repository.findById(uuid);
            if (existingResource.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Resource targetResource = existingResource.get();
            targetResource.setName(request.getName());
            targetResource.setEnd_points(request.getEnd_point());
            targetResource.setUpdated_at(LocalDateTime.now());
            repository.save(targetResource);
            return new ResponseEntity<>(mapToResourceResponse(targetResource), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
            
            

    }
    public ResponseEntity<Response> deleteResource(int uuid) {
        try {
            Optional<Resource> existingResource = repository.findById(uuid);
            if (existingResource.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Resource targetResource = existingResource.get();
            targetResource.set_deleted(true);
            repository.save(targetResource);
            return new ResponseEntity<>((Response.builder().message("Delete Successful").build()),HttpStatus.ACCEPTED); 
        } catch (Exception e) {
            return new ResponseEntity<>((Response.builder().message("Failed").build()), HttpStatus.BAD_REQUEST);
        }
          
            
        
           
        
    }
    

}
