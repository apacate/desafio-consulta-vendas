package com.devsuperior.dsmeta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

    @Autowired
    private SaleService service;

    @GetMapping(value = "/report")
    public ResponseEntity<Page<SaleDTO>> getReport(
            @RequestParam(value = "minDate", required = false) String minDate,
            @RequestParam(value = "maxDate", required = false) String maxDate,
            @RequestParam(value = "name", required = false) String name,
            Pageable pageable) {
        Page<SaleDTO> report = service.getSalesReport(minDate, maxDate, name, pageable);
        return ResponseEntity.ok(report);
    }

    @GetMapping(value = "/summary")
    public ResponseEntity<List<SaleSummaryDTO>> getSummary(
            @RequestParam(value = "minDate", required = false) String minDate,
            @RequestParam(value = "maxDate", required = false) String maxDate) {
        List<SaleSummaryDTO> summary = service.getSalesSummary(minDate, maxDate);
        return ResponseEntity.ok(summary);
    }
}


