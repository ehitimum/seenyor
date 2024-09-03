package com.yunyan.project.authorization.dto;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddPermissionsToRole {
    private Set<Integer> permission_ids;
}
