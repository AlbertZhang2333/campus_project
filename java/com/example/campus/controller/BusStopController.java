package com.example.campus.controller;

import com.example.campus.service.BusStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage")
public class BusStopController {
    @Autowired
    private BusStopService busStopService;
}
