package com.matheustorres.gestao_vagas.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheustorres.gestao_vagas.models.JobModel;

public interface JobRepository extends JpaRepository<JobModel, UUID> {
    List<JobModel> findByDescriptionContaining(String title);
}
