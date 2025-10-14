package com.example.scm.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.scm.Dao.UserRepository;
import com.example.scm.Entities.User;
import com.example.scm.Helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;






@Controller
public class HomeController {

    @GetMapping("/hhome")
    public String getMethodName() {
        System.out.println("hello world hare krishna!");
        return "hhome";
    }

    @GetMapping("/register")
    public String register() {
        System.out.println("hello world hare krishna!");
        return "register";
    }
    

    @Autowired
    private UserRepository userRepository;

    //for testing
    @GetMapping("/test")
    public String test(){
        User user = new User();
        user.setName("Jemish");
        userRepository.save(user);

        return "home";
    }

    @RequestMapping("/home")
    public String home(Model m){
        m.addAttribute("title", "Home - SCM 1.0");

        return "home.html";
    }

    @RequestMapping("/about")
    public String about(Model m){
        m.addAttribute("title", "About - SCM 1.0");

        return "about.html";
    }

    @RequestMapping("/signup")
    public String signup(Model m, HttpSession session){
        m.addAttribute("title", "Register - SCM 1.0");
        m.addAttribute("user", new User());


         Object sessionMessage = session.getAttribute("message");
    
        if (sessionMessage != null) {
            //Pass the message to the Model for Thymeleaf to display
            m.addAttribute("message", session.getAttribute("message")); 
            
            // CRITICAL: Remove the message from the session so it doesn't appear on refresh
            session.removeAttribute("message"); 
        }

        return "signup.html";
    }


    //@PostMapping("/do_registration")
    @RequestMapping(value="/do_registration", method = RequestMethod.POST)
    public String registeringUser(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value="agreement", defaultValue = "false") Boolean agreement, Model m, HttpSession session ) {
        
        try{

            if(!agreement){
                System.out.println("You have not agreed the terms and conditions");
                throw new Exception("You have not agreed the terms and conditions");
            }

            if(result.hasErrors()){
                System.out.println("Validation Errors: " + result.getAllErrors());
                m.addAttribute("user", user);
                return "signup";
            }
            //console print
            System.out.println("Agreement " + agreement);
            System.out.println("User " + user);

            //User result =
            this.userRepository.save(user);
            m.addAttribute("user", new User());
            session.setAttribute("message", new Message("successfully registeered !!", "alert-success"));
            //session.removeAttribute("message");
            return "signup.html";

        } catch(Exception e){
            e.printStackTrace();
            m.addAttribute("user", user);
            session.setAttribute("message", new Message("something went wrong !! " + e.getMessage(), "alert-danger"));
            return "signup.html";

        }

        //return "signup.html";
    }
    







    
}
