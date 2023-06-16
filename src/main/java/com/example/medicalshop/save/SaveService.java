package com.example.medicalshop.save;

import com.example.medicalshop.exceptions.NotFoundException;
import com.example.medicalshop.product.Product;
import com.example.medicalshop.product.ProductService;
import com.example.medicalshop.user.User;
import com.example.medicalshop.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SaveService {
    private final SaveRepository saveRepository;
    private final UserService userService;
    private final ProductService productService;

    public SaveService(SaveRepository saveRepository, UserService userService, ProductService productService) {
        this.saveRepository = saveRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public String save(Long userId, Long productId) {
        Optional<Save> existedSave = saveRepository.findByUser_IdAndProduct_Id(userId, productId);
        if (existedSave.isPresent()) {
            saveRepository.delete(existedSave.get());
            return "successfully deleted";
        } else {
            Optional<User> user = userService.findById(userId);
            if (user.isEmpty()) throw new NotFoundException("user does not found");

            Optional<Product> product = productService.findById(productId);
            if (product.isEmpty()) throw new NotFoundException("product does not found");

            Save save = new Save();
            save.setUser(user.get());
            save.setProduct(product.get());
            saveRepository.save(save);
            return "successfully added";
        }
    }
}
