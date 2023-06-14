package com.example.medicalshop.cart;

import com.example.medicalshop.dto.CartBody;
import com.example.medicalshop.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addToCart(@RequestBody CartBody cartBody) {
        Response<CartItem> response = cartService.addToCart(cartBody.userId, cartBody.productId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/decrease")
    public Response decrease(@RequestBody CartBody cartBody) {
        cartService.decrease(cartBody.userId, cartBody.productId);
        Response<String> response = new Response<>();
        response.setData("ok");
        response.setStatus(HttpStatus.OK.value());
        return response;
    }

    @DeleteMapping("/delete-one-product")
    public Response deleteOneProduct(@RequestBody CartBody cartBody) {
        String cartItem = cartService.deleteOneProduct(cartBody.userId, cartBody.productId);
        Response<String> response = new Response<>();
        response.setData(cartItem);
        response.setStatus(HttpStatus.OK.value());
        return response;
    }

    @DeleteMapping("/clear/{userId}")
    public Response clear(@PathVariable Long userId) {
        cartService.clear(userId);
        Response<String> response = new Response<>();
        response.setData("ok");
        response.setStatus(HttpStatus.OK.value());
        return response;
    }
}
