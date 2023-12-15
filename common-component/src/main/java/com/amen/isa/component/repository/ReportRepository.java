package com.amen.isa.component.repository;

import com.amen.isa.model.domain.Report;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReportRepository extends ReactiveMongoRepository<Report, String> {
}
