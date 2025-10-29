package com.example.scm.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import com.example.scm.Entities.User;
import com.example.scm.Helper.Helper;
import com.example.scm.Services.UserService;



@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;


    
    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication){
        System.out.println("Adding loggedin user to model.");
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logge in: {}", username);

        User user = userService.getUserByEmail(username);
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        model.addAttribute("loggedInUser", user);
    }

    //user deshboard page
    @RequestMapping(value="/dashboard", method=RequestMethod.GET)
    public String userDashboardPage() {

        return "user/dashboard";
    }

    //user profile page
    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public String userProfilePage(Model model, Authentication authentication) {
        // String name = principal.getName();
        // logger.info("User ka name: " + name);

        // String username = Helper.getEmailOfLoggedInUser(principal);
       
        

        return "user/profile";
    }
    

    //user add contact page

    //user view contacts

    //user edit contact

    //user delete contact

}
