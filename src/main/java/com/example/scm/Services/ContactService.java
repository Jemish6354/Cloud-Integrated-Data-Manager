package com.example.scm.Services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);
    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);
    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user);

    // get contacts by userId
    List<Contact> getByUserId(String userId);

    // get by user 
     Page<Contact> getByUser(User user, int page, int size, String sortField, String sortDirection);







}
