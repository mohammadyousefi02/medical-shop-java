package com.example.medicalshop.auth;

import com.example.medicalshop.dto.Login;
import com.example.medicalshop.dto.Token;
import com.example.medicalshop.user.CreateUserBody;
import com.example.medicalshop.user.User;
import com.example.medicalshop.user.UserService;
import com.example.medicalshop.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class authController {
    private UserService userService;
    private JwtUtil jwtUtil;

    public authController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Token login(@RequestBody Login login) {
        return userService.login(login);
    }

    @PostMapping("/token")
    @Authorize
    public Claims getDetail(HttpServletRequest request, @RequestBody Token token) {
        Long id = (Long) request.getAttribute("userId");
        return jwtUtil.extractAllClaims(token.getAccessToken());
//        return id;
    }


    @PostMapping("/signup")
    public Token createUser(@RequestBody CreateUserBody createUserBody) {
        User user = new User();
        user.setUsername(createUserBody.username);
        user.setEmail(createUserBody.email);
        user.setPassword(createUserBody.password);
        User newUser = userService.createUser(user);
        Token token = new Token();
        token.setAccessToken(jwtUtil.generateToken(newUser));
        return token;
    }
}
