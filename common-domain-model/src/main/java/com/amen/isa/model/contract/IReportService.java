package com.amen.isa.model.contract;

import com.amen.isa.model.domain.Basket;
import com.amen.isa.model.domain.BasketPosition;
import com.amen.isa.model.domain.Report;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface IReportService {
    Mono<Report> generateReport(String storeId);

    Mono<Report> generateReport(LocalDateTime minusDays, LocalDateTime now);
}

