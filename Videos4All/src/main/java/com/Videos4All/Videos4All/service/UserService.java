package com.Videos4All.Videos4All.service;

import com.Videos4All.Videos4All.model.User;
import com.Videos4All.Videos4All.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UsersRepo usersRepo;

    public List<User> findAll() {
        return usersRepo.findAll();
    }

    public ResponseEntity<?> addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(
                new BCryptPasswordEncoder().encode(password)
        );
        user.setAccessToken(
                new BCryptPasswordEncoder().encode(UUID.randomUUID().toString())
        );
        Optional<User> inTableUser = usersRepo.findByUsername(user.getUsername());
        if (!inTableUser.isPresent()) {
            return ResponseEntity.ok(usersRepo.save(user));
        }
        return new ResponseEntity<String>(user.getUsername() + " already exists.", HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<?> isValidUser(String username, String password) {
        Optional<User> user = usersRepo.findByUsername(username);
        if (user.isPresent() && Objects.equals(user.get().getUsername(), password)) {
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<String>("Invalid Data.", HttpStatus.NOT_FOUND);
    }


    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()));
    }

    public ResponseEntity<?> logout(User user) {
        usersRepo.findById(user.getId())
                .ifPresent(v -> {
                    v.setAccessToken(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()));
                    usersRepo.save(v);
                });
        return ResponseEntity.ok("User logged out.");
    }
}