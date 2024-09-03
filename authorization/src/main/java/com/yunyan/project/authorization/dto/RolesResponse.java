package com.yunyan.project.authorization.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.yunyan.project.authorization.model.Permission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RolesResponse {
    private int uuid;
    private String name;
    private boolean status;
    private LocalDateTime created_At;
    private LocalDateTime updated_At;
    private Set<Permission> permission_id;
}
