package com.demo.catalogservice.domain;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toResponse(Product product);
}
