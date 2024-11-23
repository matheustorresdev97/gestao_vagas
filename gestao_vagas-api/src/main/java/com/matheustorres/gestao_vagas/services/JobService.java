package com.matheustorres.gestao_vagas.services;

import java.util.List;

import com.matheustorres.gestao_vagas.models.JobModel;


public interface JobService {

    void save(JobModel jobModel);

    List<JobModel> listAllJobsByFilterUseCase(String filter);
    
}
