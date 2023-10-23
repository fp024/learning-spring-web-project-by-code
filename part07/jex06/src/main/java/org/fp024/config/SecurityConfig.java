package org.fp024.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.security.CustomLoginSuccessHandler;
import org.fp024.security.CustomUserDetailsService;
import org.fp024.type.MemberAuthType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
// @Import(ServletConfig.class)
public class SecurityConfig {

  private final DataSource dataSource;

  @Bean(name = "mvcHandlerMappingIntrospector")
  public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
    return new HandlerMappingIntrospector();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        (auths) ->
            auths
                .requestMatchers("/sample/all")
                .permitAll()
                .requestMatchers("/sample/admin")
                .hasRole(MemberAuthType.ROLE_ADMIN.getGroupName())
                .requestMatchers("/sample/member")
                .hasRole(MemberAuthType.ROLE_MEMBER.getGroupName()));

    http.formLogin()
        .loginPage("/customLogin")
        .loginProcessingUrl("/login")
        .successHandler(loginSuccessHandler());

    http.logout()
        .logoutUrl("/customLogout")
        .invalidateHttpSession(true)
        .deleteCookies("remember-me", "JSESSIONID");

    http.rememberMe()
        .key("fp024")
        .tokenRepository(persistentTokenRepository())
        .tokenValiditySeconds(604800);

    return http.build();
  }

  /*
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails admin =
        User.withUsername("admin")
            .password("admin")
            .roles(MemberAuthType.ROLE_ADMIN.getRoleName())
            .build();
    UserDetails member =
            User.withUsername("member")
            .password("$2a$10$cwpVKNhU4h1P4xPT0h1ss.yfLTwZT9PjcCpAAMEZ3ZAwwxNCuoXSS")
            .roles(MemberAuthType.ROLE_MEMBER.getRoleName())
            .build();
    return new InMemoryUserDetailsManager(admin, member);
  }
  */

  /*
  @Bean
  public UserDetailsManager users(DataSource dataSource) {
    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    users.setUsersByUsernameQuery(
        "SELECT USERID, USERPW, ENABLED FROM TBL_MEMBER WHERE USERID = ?");
    users.setAuthoritiesByUsernameQuery(
        "SELECT USERID, AUTH FROM TBL_MEMBER_AUTH WHERE USERID = ?");

    return users;
  }
  */

  @Bean
  public UserDetailsService customUserDetailsService() {
    return new CustomUserDetailsService();
  }

  @Bean
  public AuthenticationSuccessHandler loginSuccessHandler() {
    return new CustomLoginSuccessHandler();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
    jdbcTokenRepository.setDataSource(dataSource);
    return jdbcTokenRepository;
  }
}
