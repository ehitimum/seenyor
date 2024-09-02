package com.yunyan.project.authorization.dto;
import java.time.LocalDateTime;

import com.yunyan.project.authorization.model.Resource;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponse {
    private String msg;
    private int uuid;
    private String name;
    private String end_point;
    private LocalDateTime created_at;
    private LocalDateTime updated_at; 
    private Resource resource_id;
}