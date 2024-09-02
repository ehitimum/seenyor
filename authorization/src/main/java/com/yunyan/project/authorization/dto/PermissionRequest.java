package com.yunyan.project.authorization.dto;

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
public class PermissionRequest {
    @NotEmpty
    private String name;
    private String end_point;
    private Resource resource_id;
}
