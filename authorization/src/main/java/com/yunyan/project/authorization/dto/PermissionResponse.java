package com.yunyan.project.authorization.dto;
import java.time.LocalDateTime;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponse {
    private int id;
    private String name;
    private String end_point;
    private LocalDateTime created_at;
    private LocalDateTime updated_at; 
}