package com.yunyan.project.iot.dto.boundary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoundariesDTO {
    private String uid;
    private String upLeft;
    private String lowLeft;
    private String upRight;
    private String lowRight;
}
