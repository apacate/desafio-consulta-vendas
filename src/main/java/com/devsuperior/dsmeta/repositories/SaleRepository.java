package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s WHERE s.date BETWEEN :min AND :max AND LOWER(s.seller.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Sale> findSalesByDateAndSellerName(LocalDate min, LocalDate max, String name, Pageable pageable);

    @Query("SELECT s.seller.name AS sellerName, SUM(s.amount) AS total " +
           "FROM Sale s WHERE s.date BETWEEN :min AND :max GROUP BY s.seller.name")
    List<Object[]> findSalesSummaryByDate(LocalDate min, LocalDate max);
}

