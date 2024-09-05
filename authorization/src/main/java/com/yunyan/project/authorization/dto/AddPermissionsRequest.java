package com.yunyan.project.authorization.dto;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddPermissionsRequest {
    @NotEmpty
    @Pattern(regexp = "^[0-9]+$")
    private Set<Integer> permission_ids;
}
