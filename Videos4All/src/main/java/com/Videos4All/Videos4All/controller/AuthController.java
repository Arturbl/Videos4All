package com.Videos4All.Videos4All.controller;

import com.Videos4All.Videos4All.model.pojo.AuthRequest;
import com.Videos4All.Videos4All.model.pojo.AuthResponse;
import com.Videos4All.Videos4All.model.User;
import com.Videos4All.Videos4All.service.UserService;
import com.Videos4All.Videos4All.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authManager, JwtTokenUtil jwtUtil, UserService userService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping(value = "/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        System.out.println(request);
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            System.out.println("Access token: " + accessToken);
            AuthResponse response = new AuthResponse(user.getUsername(), accessToken);
            return ResponseEntity.ok().body(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
