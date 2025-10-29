package com.example.scm.Config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.scm.Dao.UserRepository;
import com.example.scm.Entities.Providers;
import com.example.scm.Entities.User;
import com.example.scm.Helper.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, 
        HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

                logger.info("OAuthAuthenticationSuccessHandler");

                // identify the provider (google, github, fb)

                var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
                
                String authorizedClientRegistrationnId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
                logger.info(authorizedClientRegistrationnId);

                var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

                oauthUser.getAttributes().forEach((key, value) -> {
                    logger.info(key + " : " + value);
                });

                User user = new User();
                user.setId(UUID.randomUUID().toString());
                user.setRoleList(List.of(AppConstants.ROLE_USER));
                user.setEmailVarified(true);
                user.setEnabled(true);

                if(authorizedClientRegistrationnId.equalsIgnoreCase("google")){
                    
                    // google
                    // google attribute

                    user.setEmail(oauthUser.getAttribute("email").toString());
                    user.setProfilePic(oauthUser.getAttribute("picture").toString());
                    user.setName(oauthUser.getAttribute("name").toString());
                    user.setProviderUserId(oauthUser.getName());
                    user.setProvider(Providers.GOOGLE);
                    user.setPassword("dummy");
                    user.setAbout("This account is created using google...");


                } else if(authorizedClientRegistrationnId.equalsIgnoreCase("github")){

                    // github
                    // github attribute

                    String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString() + "@gmail.com";
                    String picture = oauthUser.getAttribute("avatar_url");
                    String name = oauthUser.getAttribute("login").toString();
                    String providerUserId = oauthUser.getName();

                    user.setEmail(email);
                    user.setProfilePic(picture);
                    user.setName(name);
                    user.setProviderUserId(providerUserId);
                    user.setProvider(Providers.GITHUB);
                    user.setPassword("dummy");
                    user.setAbout("This account is created using github...");



                } else if(authorizedClientRegistrationnId.equalsIgnoreCase("linkedin")){

                    // linkedin
                    // linkedin attribute

                } else {
                    logger.info("OAuthAuthenticationSuceessHandler: Unknown Provider");
                }


/*
                // save data in db
                DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

                // print info in console
                // logger.info(user.getName());
                // user.getAttributes().forEach((key, value) -> {
                //     logger.info("{} => {}", key, value);
                // });
                // logger.info(user.getAuthorities().toString());

                String email = user.getAttribute("email").toString();
                String name = user.getAttribute("name").toString();
                String picture = user.getAttribute("picture").toString();

                //create user and save to db

                User user1 = new User();

                user1.setEmail(email);
                user1.setName(name);
                user1.setProfilePic(picture);
                user1.setPassword("password");
                user1.setId(UUID.randomUUID().toString());
                user1.setProvider(Providers.GOOGLE);
                user1.setEnabled(true);
                user1.setEmailVarified(true);
                user1.setProviderUserId(user1.getName());
                user1.setRoleList(List.of(AppConstants.ROLE_USER));
                user1.setAbout("This account is using google...");

                User user2 = userRepository.findByEmail(email).orElse(null);

                if(user2 == null){
                    userRepository.save(user1);
                    logger.info("User saved: " + email);
                }
*/
                



                // save the user

                
                User user2 = userRepository.findByEmail(user.getEmail()).orElse(null);

                if(user2 == null){
                    userRepository.save(user);
                    logger.info("User saved:");
                }
                
                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

                
        
                
    }
    

    // 


}
