package com.amen.isa.report.service;

import com.amen.isa.model.contract.IReportService;
import com.amen.isa.model.domain.Report;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// TODO: Write me :)
@Service
public class ReportServiceImpl implements IReportService {
    @Override
    public Report generateReport(String storeId) {
        return null;
    }

    @Override
    public Report generateReport(LocalDateTime from, LocalDateTime now) {
        return null;
    }
}
