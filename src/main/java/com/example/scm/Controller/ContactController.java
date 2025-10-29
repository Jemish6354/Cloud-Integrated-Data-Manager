package com.example.scm.Controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.scm.Entities.Contact;
import com.example.scm.Entities.User;
import com.example.scm.Helper.Helper;
import com.example.scm.Helper.Message;
import com.example.scm.Helper.MessageType;
import com.example.scm.Services.ContactService;
import com.example.scm.Services.ImageService;
import com.example.scm.Services.UserService;
import com.example.scm.UserForm.ContactForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;






@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    // add contact page
    @RequestMapping("/add")
    public String addContactView(Model model) {

        ContactForm contactForm = new ContactForm();
        // contactForm.setName("Jemish Lathiya");
        // contactForm.setFavourite(true);
        model.addAttribute("contactForm", contactForm);


        return "user/add_contact";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession session) {

        // fetch
        // validation : (via @Valid and BindingResult) 
        // save to db
        // success message (via @HttpSession)
        // redirect 

        if(result.hasErrors()){

            result.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
            .content("Please correct the following error(s)")
            .type(MessageType.red)
            .build());

            return "user/add_contact";
        }



        //convert contactForm to contact
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setFavourite(contactForm.isFavourite());

        // process the image
        logger.info("file information : {}", contactForm.getContactImage().getOriginalFilename()); // on console

        String filename = UUID.randomUUID().toString();
        String fileURL = imageService.uploadeImage(contactForm.getContactImage(), filename);
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(filename);



        // get user via Authentication
        String username= Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        contact.setUser(user);

        contactService.save(contact);
        System.out.println("saved contact details in database" + contactForm);

        session.setAttribute("message", Message.builder()
            .content("You have successfully added a new contact")
            .type(MessageType.green)
            .build());



        
        return "redirect:/user/contacts/add";
    }
    
    // view contact
    @RequestMapping
    public String viewContacts(Model model, Authentication authentication) {

        // load all the user contacts

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);

        List<Contact> contacts = contactService.getByUser(user);
        model.addAttribute("contacts", contacts);


        return "user/contacts";
    }
    
    
}
