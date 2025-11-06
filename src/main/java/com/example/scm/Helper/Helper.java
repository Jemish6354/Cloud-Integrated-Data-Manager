package com.example.scm.Helper;



import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {


    public static String getEmailOfLoggedInUser(Authentication authentication){

        

        // get email while signup via username

        // get email while signup via google

        // get email whilesignup via github

        if(authentication instanceof OAuth2AuthenticationToken){

            //hence google or github

            var aoth2AuthenticationToken  = (OAuth2AuthenticationToken) authentication;
            var clientId = aoth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User = (OAuth2User) authentication.getPrincipal();

            String username = "";

            if(clientId.equalsIgnoreCase("google")){
                System.out.println("Getting email form google");
                username = oauth2User.getAttribute("email").toString();

            } else if(clientId.equalsIgnoreCase("github")){
                System.out.println("Getting emial from github");
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString() + "@gmail.com";
                   

            }

            return username;


        } else {

            // hence via username
            System.out.println("Getting data from local database.");
            return authentication.getName();
        }
        

        
        
    }


    public static String getLinkForEmailVarification(String emailToken){

        String link = "http://localhost:8080/auth/varify-email?token=" + emailToken;

        return link;

    }
}
