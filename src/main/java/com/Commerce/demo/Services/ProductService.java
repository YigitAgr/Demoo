package com.Commerce.demo.Services;

import com.Commerce.demo.Dto.ProductDto;
import com.Commerce.demo.Models.Product;
import com.Commerce.demo.Repositorys.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public ProductDto getProduct(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            return ProductDto.fromEntity(product.get());
        }else{
            return null;
        }
    }

    public ProductDto addProduct(ProductDto productDto) {
        productDto.validate();
        Product product = productDto.toEntity();
        productRepository.save(product);
        return ProductDto.fromEntity(product);

    }

    public ProductDto updateProduct(ProductDto productDto) {
        System.out.println("içerdema");
        Optional<Product> optionalProduct = productRepository.findById(productDto.getId());
        System.out.println("productDto.getId()");
        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setProductName(productDto.getProductName());
            product.setProductPrice(productDto.getProductPrice());
            product.setProductDescription(productDto.getProductDescription());
            product.setProductStock(productDto.getProductStock());
            productRepository.save(product);
            return ProductDto.fromEntity(product);
        }else{
            throw new IllegalArgumentException("Product not found. ");
        }
    }

    public void deleteProduct(int productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found .");
        }
        productRepository.deleteById(productId);
    }

}
