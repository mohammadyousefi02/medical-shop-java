package com.example.medicalshop.cart;

import com.example.medicalshop.auth.Authorize;
import com.example.medicalshop.dto.CartBody;
import com.example.medicalshop.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    @Authorize
    public ResponseEntity<Response<CartItem>> addToCart(@RequestBody CartBody cartBody, HttpServletRequest request) {
        Response<CartItem> response = cartService.addToCart((Long) request.getAttribute("userId"), cartBody.productId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/decrease")
    @Authorize
    public Response<String> decrease(@RequestBody CartBody cartBody, HttpServletRequest request) {
        cartService.decrease((Long) request.getAttribute("userId"), cartBody.productId);
        Response<String> response = new Response<>();
        response.setData("ok");
        response.setStatus(HttpStatus.OK.value());
        return response;
    }

    @DeleteMapping("/delete-one-product")
    @Authorize
    public Response<String> deleteOneProduct(@RequestBody CartBody cartBody, HttpServletRequest request) {
        String cartItem = cartService.deleteOneProduct((Long) request.getAttribute("userId"), cartBody.productId);
        Response<String> response = new Response<>();
        response.setData(cartItem);
        response.setStatus(HttpStatus.OK.value());
        return response;
    }

    @DeleteMapping("/clear")
    @Authorize
    public Response<String> clear(HttpServletRequest request) {
        cartService.clear((Long) request.getAttribute("userId"));
        Response<String> response = new Response<>();
        response.setData("ok");
        response.setStatus(HttpStatus.OK.value());
        return response;
    }
}
