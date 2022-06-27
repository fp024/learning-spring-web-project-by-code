package org.fp024.config;

import lombok.extern.slf4j.Slf4j;
import org.fp024.security.CustomLoginSuccessHandler;
import org.fp024.type.MemberAuthType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        (auths) ->
            auths
                .antMatchers("/sample/all")
                .permitAll()
                .antMatchers("/sample/admin")
                .hasRole(MemberAuthType.ROLE_ADMIN.getRoleUserName())
                .antMatchers("/sample/member")
                .hasRole(MemberAuthType.ROLE_MEMBER.getRoleUserName()));

    http.formLogin()
        .loginPage("/customLogin")
        .loginProcessingUrl("/login")
        .successHandler(loginSuccessHandler());

    http.logout()
        .logoutUrl("/customLogout")
        .invalidateHttpSession(true)
        .deleteCookies("remember-me", "JSESSIONID");

    return http.build();
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user =
        User.withUsername("admin")
            .password("admin")
            .roles(MemberAuthType.ROLE_ADMIN.getRoleUserName())
            .username("member")
            .password("$2a$10$cwpVKNhU4h1P4xPT0h1ss.yfLTwZT9PjcCpAAMEZ3ZAwwxNCuoXSS")
            .roles(MemberAuthType.ROLE_MEMBER.getRoleUserName())
            .build();
    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public AuthenticationSuccessHandler loginSuccessHandler() {
    return new CustomLoginSuccessHandler();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
