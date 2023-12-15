package com.amen.isa.report.controller;

import com.amen.isa.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportsController {
    private final ReportService reportService;

    @GetMapping()
    public void submit() {
        log.info("Report requested");
        reportService.triggerJob();
    }
}
