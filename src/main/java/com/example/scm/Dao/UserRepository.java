package com.example.scm.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.scm.Entities.User;
// import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository <User, String> {

    // @Query("select u from User u where u.email = :email")
    // public User getUserByUserName(@Param("email") String email);

    Optional<User>  findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String Password);

    Optional<User> findByEmailToken(String token);

    
    
}
