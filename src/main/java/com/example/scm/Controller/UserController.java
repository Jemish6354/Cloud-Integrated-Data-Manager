package com.example.scm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/user")
public class UserController {

    //user deshboard page
    @RequestMapping(value="/dashboard", method=RequestMethod.GET)
    public String userDashboardPage() {

        return "user/dashboard";
    }

    //user profile page
    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public String userProfilePage() {

        return "user/profile";
    }
    

    //user add contact page

    //user view contacts

    //user edit contact

    //user delete contact

}
