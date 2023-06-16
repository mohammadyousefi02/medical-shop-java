package com.example.medicalshop.order;

import com.example.medicalshop.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "medical", name = "order_item")
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    private String title;
    private Long price;
    private int quantity;
    @Column(name = "madein")
    private String madeIn;
    private String company;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", madeIn='" + madeIn + '\'' +
                ", company='" + company + '\'' +
                ", order=" + order +
                ", product=" + product +
                '}';
    }
}
