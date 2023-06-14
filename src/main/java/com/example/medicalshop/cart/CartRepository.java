package com.example.medicalshop.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByUser_IdAndProduct_Id(Long userId, Long productId);

    int deleteCartItemByUser_IdAndProduct_Id(Long userId, Long productId);

    int deleteCartItemByUser_Id(Long userId);
}
