package com.grturbo.grturbofullstackproject.config;

import com.grturbo.grturbofullstackproject.model.entity.Authority;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.repositority.AuthorityRepository;
import com.grturbo.grturbofullstackproject.repositority.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AuthorityRepository roleRepository;

    private final UserRepository userRepository;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public GoogleOAuth2SuccessHandler(AuthorityRepository authorityRepository, UserRepository userRepository) {
        this.roleRepository = authorityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String email = token.getPrincipal().getAttributes().get("email").toString();
        if (userRepository.findUserByEmail(email).isPresent()) {

        } else {
            User user = new User();
            user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
            user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
            user.setEmail(email);

            List<Authority> roles = new ArrayList<>();

            if (userRepository.count() < 1) {
                roles.add(roleRepository.findById(1L).get());
            } else {
                roles.add(roleRepository.findById(2L).get());
            }

            user.setAuthorities(roles);
            userRepository.save(user);
        }
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
    }

}
