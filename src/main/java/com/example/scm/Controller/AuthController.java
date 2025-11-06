package com.example.scm.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.scm.Dao.UserRepository;
import com.example.scm.Entities.User;
import com.example.scm.Helper.Message;
import com.example.scm.Helper.MessageType;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="/varify-email", method=RequestMethod.GET)
    public String varifyEmail(@RequestParam("token") String token, HttpSession session) {

        User user = userRepository.findByEmailToken(token).orElse(null);

        if(user != null){
            if(user.getEmailToken().equals(token)){
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepository.save(user);
                 session.setAttribute("message", Message.builder()
                    .content("Your email is varified. Now you can login.")
                    .type(MessageType.green)
                    .build());
                return "success_page";
            }

            session.setAttribute("message", Message.builder()
                .content("Email not varified!! Something went wrong")
                .type(MessageType.red)
                .build());

            return "error_page";
            
        }

        session.setAttribute("message", Message.builder()
                .content("Email not varified!! Something went wrong")
                .type(MessageType.red)
                .build());


       


        return "error_page";
    }
    
    

}
