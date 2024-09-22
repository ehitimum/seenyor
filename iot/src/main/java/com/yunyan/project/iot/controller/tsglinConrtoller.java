package com.yunyan.project.iot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yunyan.project.iot.service.tsglinService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/tsglin")
@RequiredArgsConstructor
public class tsglinConrtoller {

    @Autowired
    private final tsglinService service;

    public tsglinConrtoller() {
        this.service = null;
    }

    public void getDeviceInfo(){
        ResponseEntity<?> response = service.getDeviceInfo();
    }

}
