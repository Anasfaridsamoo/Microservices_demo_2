package com.demo.catalogservice.domain;

import com.demo.catalogservice.ApplicationProperties;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ApplicationProperties applicationProperties;
    private final ProductMapper mapper;

    @Override
    public PagedResult<ProductResponse> findAllProducts(int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        page = page <= 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(page, applicationProperties.pageSize(), sort);
        Page<ProductResponse> pages = productRepository.findAll(pageable).map(mapper::toResponse);
        return new PagedResult<>(
                pages.getContent(),
                pages.getTotalElements(),
                pages.getNumber() + 1,
                pages.getTotalPages(),
                pages.isFirst(),
                pages.isLast(),
                pages.hasNext(),
                pages.hasPrevious());
    }

    @Override
    public Optional<ProductResponse> findByCode(String code) {
        return productRepository.findByCode(code).map(mapper::toResponse);
    }
}
