package com.example.medicalshop.order;

import com.example.medicalshop.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(schema = "medical", name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIncludeProperties({"id", "username", "email"})
    private User user;

    private String name;
    private String province;
    private String city;
    private String address;
    private Long zipcode;
    private String number;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties({"order"})
    private List<OrderItem> orderItems;

    public Order(Long id, User user, String name, String province, String city, String address, Long zipcode, String number, List<OrderItem> orderItems) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.province = province;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
        this.number = number;
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", zipcode=" + zipcode +
                ", number='" + number + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}
