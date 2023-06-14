package com.example.medicalshop.util;

import com.example.medicalshop.auth.Authorize;
import com.example.medicalshop.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Interceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    public Interceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod handlerMethod) {
            if(handlerMethod.hasMethodAnnotation(Authorize.class)) {
                String token = request.getHeader("Authorization");
                if(token == null || !token.startsWith("Bearer ") || !jwtUtil.isTokenValid(token.substring(7))) throw new NotFoundException("in bade");
                request.setAttribute("userId", jwtUtil.extractId(token.substring(7)));
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
