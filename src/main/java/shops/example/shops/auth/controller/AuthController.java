// package com.ecommerce.ecommerce.auth.controller;


// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.web.bind.annotation.*;

// import com.ecommerce.ecommerce.auth.JwtUtil;
// import com.ecommerce.ecommerce.auth.entity.User;
// import com.ecommerce.ecommerce.auth.repository.UserRepository;

// @RestController
// @RequestMapping("/auth")
// @RequiredArgsConstructor
// public class AuthController {

//     private final JwtUtil jwtUtil;
//     private final AuthenticationManager authManager;
//     private final UserRepository userRepository;

//     @PostMapping("/login")
//     public ResponseEntity<?> login(@RequestBody User user) {
//         authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//         String token = jwtUtil.generateToken(user.getUsername());
//         return ResponseEntity.ok(token);
//     }

//     @PostMapping("/register")
//     public ResponseEntity<?> register(@RequestBody User user) {
//         user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//         userRepository.save(user);
//         return ResponseEntity.ok("User registered");
//     }
// }
