package com.example.medicalshop.save;

import com.example.medicalshop.product.Product;
import com.example.medicalshop.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "medical")
@Getter
@Setter
@NoArgsConstructor
public class Save {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "save_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"saves"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Save(Long id, User user, Product product) {
        this.id = id;
        this.user = user;
        this.product = product;
    }

    @Override
    public String toString() {
        return "Save{" +
                "id=" + id +
                ", user=" + user +
                ", product=" + product +
                '}';
    }
}
