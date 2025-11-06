package com.example.report.controller;

import com.example.report.service.JrsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private JrsApiService jrsApiService;

    @GetMapping("/test-schedule")
    public String testSchedule() {
        try {
            jrsApiService.scheduleReport();
            return "Job scheduled successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}