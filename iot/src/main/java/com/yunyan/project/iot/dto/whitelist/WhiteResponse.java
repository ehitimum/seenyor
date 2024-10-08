package com.yunyan.project.iot.dto.whitelist;

import java.util.List;

import com.yunyan.project.iot.model.Whitelist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhiteResponse {
    private String code;
    private String msg;
    private List<Whitelist> data;
}
