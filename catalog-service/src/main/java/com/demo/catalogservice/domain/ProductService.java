package com.demo.catalogservice.domain;

import java.util.Optional;

public interface ProductService {

    PagedResult<ProductResponse> findAllProducts(int page);

    Optional<ProductResponse> findByCode(String code);
}
