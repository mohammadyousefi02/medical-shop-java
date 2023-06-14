package com.example.medicalshop.category;


import com.example.medicalshop.exceptions.BadRequestException;
import com.example.medicalshop.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable String id) {
        try {
            Long categoryId = Long.parseLong(id);
            Optional<Category> categoryOptional = categoryService.findById(categoryId);
            if (categoryOptional.isPresent() == false) throw new NotFoundException("peydaNashod");
            return categoryOptional.get();
        } catch (NumberFormatException ex) {
            throw new BadRequestException("requestet bade");
        }
    }
}
