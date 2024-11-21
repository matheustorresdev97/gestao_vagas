package com.matheustorres.gestao_vagas.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.matheustorres.gestao_vagas.models.JobModel;
import com.matheustorres.gestao_vagas.repositories.JobRepository;
import com.matheustorres.gestao_vagas.services.JobService;

public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public void save(JobModel jobModel) {
        jobRepository.save(jobModel);
    }
}