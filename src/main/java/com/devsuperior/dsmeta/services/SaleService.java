package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public Page<SaleDTO> getSalesReport(String minDate, String maxDate, String name, Pageable pageable) {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate max = (maxDate == null) ? today : LocalDate.parse(maxDate);
        LocalDate min = (minDate == null) ? max.minusYears(1) : LocalDate.parse(minDate);
        String searchName = (name == null) ? "" : name;

        Page<Sale> result = repository.findSalesByDateAndSellerName(min, max, searchName, pageable);
        return result.map(SaleDTO::new);
    }

    public List<SaleSummaryDTO> getSalesSummary(String minDate, String maxDate) {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate max = (maxDate == null) ? today : LocalDate.parse(maxDate);
        LocalDate min = (minDate == null) ? max.minusYears(1) : LocalDate.parse(minDate);

        List<Object[]> result = repository.findSalesSummaryByDate(min, max);

        return result.stream()
                .map(obj -> new SaleSummaryDTO((String) obj[0], (Double) obj[1]))
                .collect(Collectors.toList());
    }
}

