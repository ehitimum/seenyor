package com.yunyan.project.authorization.dto.commons;

import lombok.Data;
import lombok.Builder;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldErrorDTO<T> {
    private String fieldName;
    private List<String> errors;
}
