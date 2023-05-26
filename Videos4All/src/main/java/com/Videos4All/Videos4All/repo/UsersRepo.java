package com.Videos4All.Videos4All.repo;

import com.Videos4All.Videos4All.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users, String> {

    Users findByUsername(String username);

}
