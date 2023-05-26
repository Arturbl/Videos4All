package com.Videos4All.Videos4All.service;

import com.Videos4All.Videos4All.model.Users;
import com.Videos4All.Videos4All.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UsersRepo usersRepo;

    public List<Users> findAll() {
        return usersRepo.findAll();
    }

    public ResponseEntity<?> addUser(String username, String password) {
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        Users inTableUser = usersRepo.findByUsername(user.getUsername());
        if(inTableUser == null) {
            return ResponseEntity.ok(usersRepo.save(user));
        }
        return new ResponseEntity<String>(user.getUsername() + " already exists.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> isValidUser(String username, String password) {
        Optional<Users> user = Optional.ofNullable(usersRepo.findByUsername(username));
        if(user.isPresent() && Objects.equals(user.get().getUsername(), password)){
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<String>("Invalid Data.", HttpStatus.NOT_FOUND) ;
    }

}
