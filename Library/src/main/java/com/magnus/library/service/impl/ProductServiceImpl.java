package com.magnus.library.service.impl;

import com.magnus.library.dto.ProductDto;
import com.magnus.library.model.Product;
import com.magnus.library.repository.ProductRepository;
import com.magnus.library.service.ProductService;
import com.magnus.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repo;

    @Autowired
    private ImageUpload imageUpload;

    @Override
    public List<ProductDto> findAll() {
        List<Product> products = repo.findAll();
        List<ProductDto> productDtoList = transfer(products);

        return productDtoList;
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        Product product = new Product();

        try {
            if(imageProduct == null || imageProduct.isEmpty()) {
                product.setImage(null);
            } else {
                if (imageUpload.isUploadImage(imageProduct)) {
                    System.out.println("Upload successfully");
                }
                product.setImage(Base64.
                        getEncoder().
                        encodeToString(imageProduct.getBytes()));
            }

            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.set_activated(true);
            product.set_deleted(false);
            return repo.save(product);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(ProductDto productDto, MultipartFile imageProduct) {
        try {

            Product product = repo.getReferenceById(productDto.getId());
            if (imageProduct == null) {
                product.setImage(product.getImage());
            } else {

                if (!imageUpload.checkExisted(imageProduct)) {
                    System.out.println("Update to folder");
                    imageUpload.isUploadImage(imageProduct);
                }
                System.out.println("Image exits");
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }

            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setSalePrice(productDto.getSalePrice());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCategory(productDto.getCategory());
            return repo.save(product);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = repo.getReferenceById(id);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setImage(product.getImage());
        productDto.setDeleted(product.is_deleted());
        productDto.setActivated(product.is_activated());

        return productDto;
    }

    @Override
    public void deleteById(Long id) {
        Product product = repo.getReferenceById(id);
        product.set_deleted(true);
        product.set_activated(false);
        repo.save(product);
    }

    @Override
    public void enableById(Long id) {
        Product product = repo.getReferenceById(id);
        product.set_activated(true);
        product.set_deleted(false);
        repo.save(product);
    }

    @Override
    public Page<ProductDto> pageProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> products = transfer(repo.findAll());

        Page<ProductDto> productPages = toPage(products, pageable);

        return productPages;
    }

    @Override
    public Page<ProductDto> searchProducts(int pageNumber, String keyword) {
        Pageable pageable = PageRequest.of(pageNumber, 5);
        List<ProductDto> productDtoList = transfer(repo.searchProductsList(keyword));
        Page<ProductDto> products = toPage(productDtoList, pageable);

        return products;
    }

    private Page toPage(List<ProductDto> list, Pageable pageable) {
        if(pageable.getOffset() >=list.size()) {
            return Page.empty();
        }

        int startIndex = (int) pageable.getOffset();

        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());

        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    private List<ProductDto> transfer(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setImage(product.getImage());
            productDto.setDeleted(product.is_deleted());
            productDto.setActivated(product.is_activated());

            productDtoList.add(productDto);
        }
        return productDtoList;
    }
}
