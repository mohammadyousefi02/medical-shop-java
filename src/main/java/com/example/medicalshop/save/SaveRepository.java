package com.example.medicalshop.save;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaveRepository extends JpaRepository<Save, Long> {
    Optional<Save> findByUser_IdAndProduct_Id(Long userId, Long productId);
}
