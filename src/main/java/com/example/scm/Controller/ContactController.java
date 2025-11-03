package com.example.scm.Controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.scm.Entities.Contact;
import com.example.scm.Entities.User;
import com.example.scm.Helper.AppConstants;
import com.example.scm.Helper.Helper;
import com.example.scm.Helper.Message;
import com.example.scm.Helper.MessageType;
import com.example.scm.Services.ContactService;
import com.example.scm.Services.ImageService;
import com.example.scm.Services.UserService;
import com.example.scm.UserForm.ContactForm;
import com.example.scm.UserForm.ContactSearchFrom;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;







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

        if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty() ){
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadeImage(contactForm.getContactImage(), filename);

            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);
        }



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
    public String viewContacts(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
        @RequestParam(value = "sortBy", defaultValue = "name" ) String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        Model model, Authentication authentication) {

        // load all the user contacts

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);

        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);
        // pageContact.
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

         model.addAttribute("contactSearchForm", new ContactSearchFrom());


        return "user/contacts";
    }

    @RequestMapping(value="/search", method=RequestMethod.GET)
    public String searchHandler(
            @ModelAttribute ContactSearchFrom contactSearchFrom,
            
            @RequestParam(value="size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value="order", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication
            
            ){

        logger.info("field : {} and keyword : {}",contactSearchFrom.getField(), contactSearchFrom.getValue());

        // contactService.search();

        String username = Helper.getEmailOfLoggedInUser(authentication);
        var user = userService.getUserByEmail(username);


            
        Page<Contact> pageContact = null;
        if(contactSearchFrom.getField().equalsIgnoreCase("name")){
            pageContact = contactService.searchByName(contactSearchFrom.getValue(), size, page, sortBy, direction, user);
        } else if(contactSearchFrom.getField().equalsIgnoreCase("email")){
            pageContact = contactService.searchByEmail(contactSearchFrom.getValue(), size, page, sortBy, direction, user);
        } else if(contactSearchFrom.getField().equalsIgnoreCase("phone")){
            pageContact = contactService.searchByPhoneNumber(contactSearchFrom.getValue(), size, page, sortBy, direction, user);
        } 
        
        logger.info("pageContact : {}", pageContact);
        model.addAttribute("contactSearchForm", contactSearchFrom);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("pageContact", pageContact);

    

        return "user/search";
    }

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
        @PathVariable String contactId,
        HttpSession session){

            contactService.delete(contactId);
            logger.info("contactId {} deleted successfully",contactId);

            session.setAttribute("message", 
                Message.builder()
                .content("Contact is Deleted successfully")
                .type(MessageType.green)
                .build());

        return "redirect:/user/contacts";

    }

    // update contact form view
    @GetMapping("/view/{contactId}")
    public String updateContact(
        @PathVariable String contactId,
        Model model) {

            var contact = contactService.getById(contactId);
            ContactForm contactForm = new ContactForm();
            contactForm.setName(contact.getName());
            contactForm.setEmail(contact.getEmail());
            contactForm.setPhoneNumber(contact.getPhoneNumber());
            contactForm.setAddress(contact.getAddress());
            contactForm.setDescription(contact.getDescription());
            contactForm.setFavourite(contact.isFavourite());
            contactForm.setWebsiteLink(contact.getWebsiteLink());
            contactForm.setLinkedinLink(contact.getLinkedinLink());
            contactForm.setPicture(contact.getPicture());

            model.addAttribute("contactForm", contactForm);
            model.addAttribute("contactId", contactId);



        return "user/update_contact_view";
    }

    @RequestMapping(value="/update/{contactId}", method=RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
        @Valid @ModelAttribute ContactForm contactForm,
        BindingResult bindingResult,
        Model model
        
            ) {

                if(bindingResult.hasErrors()){
                    return "user/update_contact_view";
                }

                // update the data

                var con = contactService.getById(contactId);

                con.setId(contactId);
                con.setName(contactForm.getName());
                con.setEmail(contactForm.getEmail());
                con.setPhoneNumber(contactForm.getPhoneNumber());
                con.setAddress(contactForm.getAddress());
                con.setDescription(contactForm.getDescription());
                con.setFavourite(contactForm.isFavourite());
                con.setWebsiteLink(contactForm.getWebsiteLink());
                con.setLinkedinLink(contactForm.getLinkedinLink());
                

                // process the image

                
               if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()){
                    logger.info("file is not empty not empty not empty");
                    String filename = UUID.randomUUID().toString();
                    String imageURL = imageService.uploadeImage(contactForm.getContactImage(), filename);
                    con.setPicture(imageURL);
                    con.setCloudinaryImagePublicId(filename);
                    contactForm.setPicture(imageURL);
                    
               } else {
                logger.info("file is empty");
               }

                



                var updatedCon = contactService.updateContact(con);

                System.out.println(con.getLinkedinLink());
                

                logger.info("updated contact {}",updatedCon);
                model.addAttribute("message",Message.builder()
                .content("Contact Updated")
                .type(MessageType.green)
                .build());

        return "redirect:/user/contacts/view/"+contactId;
    }
    
    
    
    
    
    
}
