package com.example.medicalshop.cart;

import com.example.medicalshop.exceptions.NotFoundException;
import com.example.medicalshop.product.Product;
import com.example.medicalshop.product.ProductService;
import com.example.medicalshop.response.Response;
import com.example.medicalshop.user.User;
import com.example.medicalshop.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {
    private CartRepository cartRepository;
    private UserService userService;
    private ProductService productService;


    @Autowired
    public CartService(CartRepository cartRepository, UserService userService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional
    public Response<CartItem> addToCart(Long userId, Long productId) {
        Response<CartItem> response = new Response<>();
        Optional<CartItem> cartItem = cartRepository.findByUser_IdAndProduct_Id(userId, productId);
        if (cartItem.isPresent()) {
            cartItem.get().increaseQuantity();
            response.setData(cartRepository.save(cartItem.get()));
            response.setStatus(HttpStatus.OK.value());
            return response;
        } else {
            Optional<User> user = Optional.ofNullable(userService.findById(userId).orElseThrow(() -> new NotFoundException("User not found")));
            Optional<Product> product = Optional.ofNullable(productService.findById(productId).orElseThrow(() -> new NotFoundException("Product not found")));
            CartItem newCartItem = new CartItem();
            newCartItem.setQuantity(1);
            newCartItem.setUser(user.get());
            newCartItem.setProduct(product.get());
            newCartItem = cartRepository.save(newCartItem);
            response.setData(newCartItem);
            response.setStatus(HttpStatus.CREATED.value());
            return response;
        }
    }

    @Transactional
    public CartItem decrease(Long userId, Long productId) {
        Optional<CartItem> cartItem = Optional.ofNullable(cartRepository.findByUser_IdAndProduct_Id(userId, productId).orElseThrow(() -> new NotFoundException("not found")));
        cartItem.get().decreaseQuantity();
        if(cartItem.get().getQuantity() == 0) {
            cartRepository.delete(cartItem.get());
            return cartItem.get();
        }
        return cartRepository.save(cartItem.get());
    }

    @Transactional
    public String deleteOneProduct(Long userId, Long productId) {
//       int cartItem = cartRepository.deleteCartItemByUser_IdAndProduct_Id(userId, productId);
        Optional<CartItem> cartItem = Optional.ofNullable(cartRepository.findByUser_IdAndProduct_Id(userId, productId).orElseThrow(() -> new NotFoundException("not found")));
        cartRepository.delete(cartItem.get());
       return "ok";
    }

    @Transactional
    public String clear(Long userId) {
        cartRepository.deleteByUser_Id(userId);
        return "ok";
    }
}
