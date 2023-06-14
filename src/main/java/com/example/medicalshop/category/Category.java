package com.example.medicalshop.category;

import com.example.medicalshop.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(schema = "medical")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String title;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.EAGER)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Category> subcategories;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Category parentCategory;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "product_category",
            schema = "medical",
            inverseJoinColumns = {@JoinColumn(name = "product_id")},
            joinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<Product> products;

    public Category(String title, List<Category> category, Category parentCategory, List<Product> products) {
        this.title = title;
        this.subcategories = category;
        this.parentCategory = parentCategory;
        this.products = products;
    }

    public Category getParentCategory() {
        if (parentCategory != null) {
            Category newCategory = new Category();
//            newCategory.setCategories(parentCategory.getCategories());
            newCategory.setId(parentCategory.getId());
            newCategory.setTitle(parentCategory.getTitle());
            return newCategory;
        }
        return null;
    }


    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", parentCategory=" + parentCategory +
                '}';
    }

}
