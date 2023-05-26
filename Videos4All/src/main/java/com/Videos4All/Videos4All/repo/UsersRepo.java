package com.Videos4All.Videos4All.repo;

import com.Videos4All.Videos4All.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<User, String> {

    User findByUsername(String username);

}
