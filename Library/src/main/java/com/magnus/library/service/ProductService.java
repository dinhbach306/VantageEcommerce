package com.magnus.library.service;

import com.magnus.library.dto.ProductDto;
import com.magnus.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();
    Product save(MultipartFile imageProduct, ProductDto productDto);
    Product update(ProductDto productDto, MultipartFile imageProduct);

    ProductDto getById(Long id);
    void deleteById(Long id);
    void enableById(Long id);

//    Paging
    Page<ProductDto> pageProducts(int pageNo);

    Page<ProductDto> searchProducts(int pageNumber, String keyword);
}
