package com.amen.isa.model.contract;

import com.amen.isa.model.domain.Basket;
import com.amen.isa.model.domain.BasketPosition;
import com.amen.isa.model.domain.Report;

import java.time.LocalDateTime;

public interface IReportService {
    Report generateReport(String storeId);

    Report generateReport(LocalDateTime minusDays, LocalDateTime now);
}

