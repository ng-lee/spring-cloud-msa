package com.catalogservice.controller;

import com.catalogservice.service.CatalogService;
import com.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog-service")
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/health-check")
    public String status() {
        return "It's working in Catalog Service";
    }

    @GetMapping("/catalogs")
    private ResponseEntity<List<ResponseCatalog>> getCatalogs() {
        List<ResponseCatalog> list = catalogService.getAllCatalogs().stream().map(catalog -> ResponseCatalog.builder()
                .productId(catalog.getProductId())
                .productName(catalog.getProductName())
                .unitPrice(catalog.getUnitPrice())
                .stock(catalog.getStock())
                .createdAt(catalog.getCreatedAt()).build()).toList();

        return ResponseEntity.ok(list);
    }
}
