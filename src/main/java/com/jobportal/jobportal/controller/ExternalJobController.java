package com.jobportal.jobportal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.jobportal.service.ExternalJobService;

@RestController
@RequestMapping("/external-jobs")
public class ExternalJobController {

    private final ExternalJobService externalJobService;

    public ExternalJobController(ExternalJobService externalJobService) {
        this.externalJobService = externalJobService;
    }

    @PostMapping("/sync")
    public String syncAll() {
        int created = externalJobService.syncExternalJobs();
        return "Imported " + created + " external jobs.";
    }

    @GetMapping("/sync")
    public String syncAllGet() {
        return syncAll();
    }
}

