package com.yunyan.project.authorization.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String end_point;
}
