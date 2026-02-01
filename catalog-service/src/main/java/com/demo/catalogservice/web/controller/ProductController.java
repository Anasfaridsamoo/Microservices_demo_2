package com.demo.catalogservice.web.controller;

import com.demo.catalogservice.domain.PagedResult;
import com.demo.catalogservice.domain.ProductNotFoundException;
import com.demo.catalogservice.domain.ProductResponse;
import com.demo.catalogservice.domain.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    ResponseEntity<PagedResult<ProductResponse>> getAllProducts(
            @RequestParam(name = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok().body(productService.findAllProducts(page));
    }

    @GetMapping("/{code}")
    ResponseEntity<ProductResponse> findByCode(@PathVariable String code) {
        return productService
                .findByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
