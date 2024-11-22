package com.matheustorres.gestao_vagas.controllers;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheustorres.gestao_vagas.dtos.JobRequestDTO;
import com.matheustorres.gestao_vagas.models.JobModel;
import com.matheustorres.gestao_vagas.services.JobService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    JobService jobService;

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    public void createJob(@Valid @RequestBody JobRequestDTO jobRequestDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        var jobModel = new JobModel();
        BeanUtils.copyProperties(jobRequestDTO, jobModel);
        jobModel.setCompanyId(UUID.fromString(companyId.toString()));
        jobService.save(jobModel);
    }
}
