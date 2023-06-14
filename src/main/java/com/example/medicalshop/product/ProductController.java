package com.example.medicalshop.product;

import com.example.medicalshop.exceptions.BadRequestException;
import com.example.medicalshop.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable() String id) {
        try {
            Long product_id = Long.parseLong(id);
            Optional<Product> product = productService.findById(product_id);
            if (product.isPresent() == false) throw new NotFoundException("peyda nashod");
            return product.get();
        } catch (NumberFormatException e) {
            throw new BadRequestException("requestet bade");
        }
    }

    @PutMapping
    public Product editProduct(@RequestBody Product product) {
        return productService.save(product);
    }
}
