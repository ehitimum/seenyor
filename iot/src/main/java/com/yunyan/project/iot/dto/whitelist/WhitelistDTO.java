package com.yunyan.project.iot.dto.whitelist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class WhitelistDTO {
    private String uid;
    private String mobile;
}
