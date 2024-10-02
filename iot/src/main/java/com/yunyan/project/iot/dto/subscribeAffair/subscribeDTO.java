package com.yunyan.project.iot.dto.subscribeAffair;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class subscribeDTO {
    private List<String> event;
    private String humanUrl;
    private String eventUrl;
}
