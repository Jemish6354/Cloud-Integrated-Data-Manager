package com.example.scm.Services;

import java.util.List;

import com.example.scm.Entities.Contact;
import com.example.scm.Entities.User;


public interface ContactService {

    // save contact
    Contact save(Contact contact);

    // update contact
    Contact updateContact(Contact contact);

    // get contacts
    List<Contact> getAll();

    // get contact by id
    Contact getById(String id);

    // delete contact
    void delete(String id);

    // search contact 
    List<Contact> search(String name, String email, String phoneNumber);

    // get contacts by userId
    List<Contact> getByUserId(String userId);

    // get by user 
     List<Contact> getByUser(User user);





}
