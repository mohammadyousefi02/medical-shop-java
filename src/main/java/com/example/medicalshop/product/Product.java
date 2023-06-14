package com.example.medicalshop.product;

import com.example.medicalshop.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(schema = "medical")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String title;

    private String description;
    private Long price;
    private int stock;
    private String madein;
    private String company;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Image> images;

    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private List<Category> categories;

    public Product(String title, String description, Long price, int stock, String madein, String company, List<Image> images, List<Category> categories) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.madein = madein;
        this.company = company;
        this.images = images;
        this.categories = categories;
    }

    public List<String> getImages() {
        return images.stream().map(i -> i.getUrl()).toList();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", madein='" + madein + '\'' +
                ", company='" + company + '\'' +
                '}';
    }

}
