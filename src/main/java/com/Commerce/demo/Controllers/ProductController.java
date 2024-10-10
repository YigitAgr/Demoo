package com.Commerce.demo.Controllers;

import com.Commerce.demo.Dto.ProductDto;
import com.Commerce.demo.Models.Product;
import com.Commerce.demo.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {


    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/getProduct")
    public ResponseEntity<ProductDto> getProduct(@RequestParam int productId) {
        ProductDto response = productService.getProduct(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/createProduct")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto response = productService.addProduct(productDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        System.out.println(productDto);
        ProductDto response = productService.updateProduct(productDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<Void> deleteProduct(@RequestParam int productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
