package com.example.medicalshop.user;

import com.example.medicalshop.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) throw new NotFoundException("peyda nashod");
        return userOptional.get();
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
