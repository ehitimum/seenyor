package com.yunyan.project.authorization.dto;

import java.time.LocalDateTime;

import com.yunyan.project.authorization.model.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceResponse {
    private int uuid;
    private String name;
    private String end_point;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Roles role;
}

