package com.grturbo.grturbofullstackproject.config;

import com.grturbo.grturbofullstackproject.model.entity.UserRole;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.repositority.UserRoleRepository;
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
import java.util.Collections;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRoleRepository roleRepository;

    private final UserRepository userRepository;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public GoogleOAuth2SuccessHandler(UserRoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String email = token.getPrincipal().getAttributes().get("email").toString();

        userRepository.findUserByEmail(email).ifPresentOrElse(
                user -> {},
                () -> {
                    User user = new User();
                    user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
                    user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
                    user.setEmail(email);

                    UserRole userRole = roleRepository.findById(2L).orElseThrow();

                    user.setRoles(Collections.singletonList(userRole));
                    userRepository.save(user);
                }
        );

        redirectStrategy.sendRedirect(request, response, "/");
    }
}
