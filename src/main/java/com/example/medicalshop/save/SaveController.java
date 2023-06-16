package com.example.medicalshop.save;

import com.example.medicalshop.auth.Authorize;
import com.example.medicalshop.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/save")
public class SaveController {
    private final SaveService saveService;

    public SaveController(SaveService saveService) {
        this.saveService = saveService;
    }

    @PostMapping("/{productId}")
    @Authorize
    public Response<String> save(@PathVariable Long productId, HttpServletRequest request) {
        Response<String> response = new Response<>();
        String result = saveService.save((Long) request.getAttribute("userId"), productId);
        response.setStatus(200);
        response.setData(result);
        return response;
    }
}
