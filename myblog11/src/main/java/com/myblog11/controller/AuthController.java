package com.myblog11.controller;

import com.myblog11.entity.Role;
import com.myblog11.entity.User;
import com.myblog11.payload.LoginDto;
import com.myblog11.payload.SignUpDto;
import com.myblog11.repository.RoleRepository;
import com.myblog11.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
// http://localhost:8080/api/auth
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository; // Autowired RoleRepository
    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody SignUpDto signUpDto) {

        // Check if the username already exists
        if (userRepo.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        // Check if the email already exists
        if (userRepo.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // Create a new User object and set properties
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        // Fetch the role (handle the case where it's not found)
        Role role = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Assign the role to the user
        user.setRoles(Collections.singleton(role));

        // Save the user to the database
        User savedUser = userRepo.save(user);

        // Return success message
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }


    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto
                                                           loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new
                        UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                        loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.",
                HttpStatus.OK);
    }
}

