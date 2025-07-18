package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.service.UserService;

@RestController
public class HelloController {
	private final AuthenticationManager customAuthenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

   
	public HelloController(@Qualifier("customAuthenticationManager") AuthenticationManager authManager) {
        this.customAuthenticationManager = authManager;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        try {
            Authentication auth = customAuthenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            String token = jwtTokenUtil.generateToken(auth.getName());
            return ResponseEntity.ok(ApiResponse.success(new AuthResponse(token)));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.failure("Invalid username or password"));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> hello(@RequestParam(defaultValue = "world") String name) {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);    
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld(@RequestParam(defaultValue = "world") String name) {
        return ResponseEntity.ok("Hello, " + name + "!");
    }

}
