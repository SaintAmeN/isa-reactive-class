package com.amen.isa.report.service;

import com.amen.isa.model.contract.IReportService;
import com.amen.isa.model.domain.Report;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

// TODO: Write me :)
@Service
public class ReportServiceImpl implements IReportService {
    @Override
    public Mono<Report> generateReport(String storeId) {
        return null;
    }

    @Override
    public Mono<Report> generateReport(LocalDateTime from, LocalDateTime now) {
        return null;
    }
}
