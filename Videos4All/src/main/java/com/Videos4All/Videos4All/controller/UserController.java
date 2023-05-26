package com.Videos4All.Videos4All.controller;

import com.Videos4All.Videos4All.model.Users;
import com.Videos4All.Videos4All.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/findAll")
    public List<Users> findAll() {
        return userService.findAll();
    }

    @GetMapping(value = "/isValidUser/{username}/{password}")
    public ResponseEntity<?> isValidUser(@PathVariable String username, @PathVariable String password) {
        ResponseEntity<?> response = userService.isValidUser(username, password);
        return response;
    }

    @PostMapping(value = "/addUser/{username}/{password}")
    public ResponseEntity<?> addUser(@PathVariable String username, @PathVariable String password) {
        ResponseEntity<?> response = userService.addUser(username, password);
        return response;
    }

}
