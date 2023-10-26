package com.ruturaj.quizappbackend.repository;

import com.ruturaj.quizappbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
     User findByUsername(String username);

     User findByEmail(String email);

     User findByEmailAndUsername(String username,String email);

}
