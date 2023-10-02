package com.grturbo.grturbofullstackproject.config;

import com.grturbo.grturbofullstackproject.service.CustomUserDetailService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;

    private final CustomUserDetailService customUserDetailService;

    public SecurityConfig(GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler, CustomUserDetailService customUserDetailService) {
        this.googleOAuth2SuccessHandler = googleOAuth2SuccessHandler;
        this.customUserDetailService = customUserDetailService;
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/shop/**", "/register", "/about", "/contactus", "/faq").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .failureUrl("/login?error")
//                .defaultSuccessUrl("/")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .and()
//                .oauth2Login()
//                .loginPage("/login")
//                .successHandler(googleOAuth2SuccessHandler)
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .and()
//                .exceptionHandling()
//                .and()
//                .csrf()
//                .disable();
//        http.headers().frameOptions().disable();
//
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                        authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/", "/index", "/shop/**", "/register", "/about", "/contactus", "/faq").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .successHandler(googleOAuth2SuccessHandler)
                .failureUrl("/login?error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .and()
                .csrf();


        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder cryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailService);
//    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/resources/**", "/static/**", "/images/**", "/productImages/**", "/css/**", "/js/**");
    }

//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/resources/**", "/static/**", "/images/**", "/productImages/**", "/css/**", "/js/**");
//    }
}