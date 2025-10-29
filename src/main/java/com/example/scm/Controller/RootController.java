package com.example.scm.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.scm.Entities.User;
import com.example.scm.Helper.Helper;
import com.example.scm.Services.UserService;

@ControllerAdvice
//all methods run for any method run (for all controller methods)
public class RootController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication){

        if(authentication == null){
            return;
        }
        System.out.println("Adding loggedin user to model.");
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logge in: {}", username);

        User user = userService.getUserByEmail(username);

        System.out.println(user);

        System.out.println(user.getName());
        System.out.println(user.getEmail());
        model.addAttribute("loggedInUser", user);
       
        
    }


}
