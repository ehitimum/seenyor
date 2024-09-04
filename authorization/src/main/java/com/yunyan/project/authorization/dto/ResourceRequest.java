package com.yunyan.project.authorization.dto;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Pattern(regexp = "^[a-zA-Z ]+$")
    @Size(min = 3, max = 50)
    private String name;

    @NotEmpty
    @URL
    @Size(max = 255)
    private String end_point;
}
