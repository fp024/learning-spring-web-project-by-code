package org.fp024.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.MemberAuthType;
import org.fp024.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

  private final DataSource dataSource;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        (auths) ->
            auths
                .requestMatchers(antMatcher("/sample/all"))
                .permitAll()
                .requestMatchers(antMatcher("/sample/admin"))
                .hasRole(MemberAuthType.ROLE_ADMIN.getGroupName())
                .requestMatchers(antMatcher("/sample/member"))
                .hasRole(MemberAuthType.ROLE_MEMBER.getGroupName()));

    http.formLogin()
        .loginPage("/customLogin")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/board/list");

    http.logout()
        .logoutUrl("/logout")
        .invalidateHttpSession(true)
        .deleteCookies("remember-me", "JSESSIONID")
        .logoutSuccessUrl("/board/list");

    http.rememberMe()
        .key("fp024")
        .tokenRepository(persistentTokenRepository())
        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7));

    return http.build();
  }

  /*
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

  /*
  @Bean
  public AuthenticationSuccessHandler loginSuccessHandler() {
    return new CustomLoginSuccessHandler();
  }
  */

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
