package com.yunyan.project.authorization.dto.commons;
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
public class ConnectPermissionsDTO {
    @NotEmpty
    @Pattern(regexp = "^[0-9]+$")
    private Set<Integer> permission_ids;
}
