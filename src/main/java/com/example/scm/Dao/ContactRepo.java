package com.example.scm.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.scm.Entities.Contact;
import com.example.scm.Entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    // find the contacts by user 
    // this is custom finder method
    // User nam nu variable chhe in contact class(entity) => no need to code (auto)
    List<Contact> findByUser(User user);

    // find the contacts by userId
    // this is custom query method
    // no field in contact class => need to code
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userIdd")
    List<Contact> findByUserId(@Param("userIdd") String userId);

}
