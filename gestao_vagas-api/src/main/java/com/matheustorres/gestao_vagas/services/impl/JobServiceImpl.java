package com.matheustorres.gestao_vagas.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheustorres.gestao_vagas.models.JobModel;
import com.matheustorres.gestao_vagas.repositories.JobRepository;
import com.matheustorres.gestao_vagas.services.JobService;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public void save(JobModel jobModel) {
        jobRepository.save(jobModel);
    }

    @Override
    public List<JobModel> listAllJobsByFilterUseCase(String filter) {
        return jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}