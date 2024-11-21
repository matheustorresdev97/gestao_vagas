package com.matheustorres.gestao_vagas.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheustorres.gestao_vagas.dtos.JobRequestDTO;
import com.matheustorres.gestao_vagas.models.JobModel;
import com.matheustorres.gestao_vagas.services.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    JobService jobService;

    @PostMapping
    public void createJob(@Valid @RequestBody JobRequestDTO jobRequestDTO) {
        var jobModel = new JobModel();
        BeanUtils.copyProperties(jobRequestDTO, jobModel);
        jobService.save(jobModel);
    }
}
