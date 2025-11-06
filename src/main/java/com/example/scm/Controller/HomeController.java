package com.example.scm.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


import com.example.scm.Entities.User;
import com.example.scm.Helper.Message;
import com.example.scm.Helper.MessageType;
import com.example.scm.Services.UserService;
import com.example.scm.UserForm.UserForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        return "redirect:/home";
    }    

    @RequestMapping("/home")
    public String home(){
        return "home.html";
    }

    @RequestMapping("/about")
    public String about(){
        System.out.println("about page");
        return "about.html";
    }

    @GetMapping("/services")
    public String servicesPage() {
        System.out.println("hservices page");
        return "services";
    }
    
    @GetMapping("/contact")
    public String conatactPage() {
        System.out.println("contact page");
        return "contact";
    }

    @GetMapping("/login") 
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")//==signup
    public String registerPage(Model model) {

        //black data form
        // userForm.setName("Jemish"); --> default data
        UserForm  userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        
        return "register";
    }







    //@PostMapping("/do_registration")
    @RequestMapping(value="/do_registration", method = RequestMethod.POST)
    public String registeringUser(@Valid @ModelAttribute UserForm userForm, BindingResult result, HttpSession session) {
        System.out.println("processing registration");

        if(result.hasErrors()){
            return "register";
        }
        
        // fetch the data
        // validate the form
        // save to db
        // message = "Registration successful"
        // redirect login page

        // step1 fetch the data via ModelAttribute
        // Step2 validate the form
        // pending
        
        // step3 save to db
        // userForm -> user
   
        //does not take default value
        // User user = User.builder()
        //     .name(userForm.getName())
        //     .email(userForm.getEmail())
        //     .password(userForm.getPassword())
        //     .about(userForm.getAbout())
        //     .phoneNumber(userForm.getPhoneNumber())
        //     .profilePic("")
        //     .build();
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());
        user.setProfilePic("http://dummoyurl.com");

        user.setEnabled(false);
        

        User savedUser = userService.saveUser(user);
        System.out.println("saved user");

        // setp4 show success message via HttpSession
        Message message = Message.builder().content("Registration Sucessfully.").type(MessageType.green).build();
        session.setAttribute("message", message);

        //step5 redirect login page
        return "redirect:/register";
    }
    







    
}
