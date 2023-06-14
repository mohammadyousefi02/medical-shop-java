package com.example.medicalshop.user;


import com.example.medicalshop.dto.Login;
import com.example.medicalshop.dto.Token;
import com.example.medicalshop.exceptions.BadRequestException;
import com.example.medicalshop.exceptions.NotFoundException;
import com.example.medicalshop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        Optional<User> existedUser = findByEmail(user.getEmail());
        if (existedUser.isPresent()) throw new BadRequestException("email is already exist");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Token login(Login login) {
        Optional<User> user = Optional.ofNullable(findByEmail(login.getEmail()).orElseThrow(() -> new NotFoundException("username or password is incorrect")));
        if (!passwordEncoder.matches(login.getPassword(), user.get().getPassword()))
            throw new NotFoundException("username or password is incorrect");
        Token token = new Token();
        token.setAccessToken(jwtUtil.generateToken(user.get()));
        return token;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String deleteById(Long id) {
        userRepository.deleteById(id);
        return "ok";
    }
}
