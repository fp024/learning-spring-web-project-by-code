package org.fp024.config;

import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.fp024.security.CustomUserDetailsService;
import org.fp024.service.MemberService;
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
public class SecurityConfig {
  private final DataSource dataSource;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(
        formLoginConfigurer ->
            formLoginConfigurer
                .loginPage("/customLogin")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/board/list"));

    http.logout(
        logoutConfigurer ->
            logoutConfigurer
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("remember-me", "JSESSIONID")
                .logoutSuccessUrl("/board/list"));

    http.rememberMe(
        rememberMeConfigurer ->
            rememberMeConfigurer
                .key("fp024")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7)));

    return http.build();
  }

  @Bean
  public UserDetailsService customUserDetailsService(MemberService memberService) {
    return new CustomUserDetailsService(memberService);
  }

  // 💡 PasswordEncoder를 BCryptPasswordEncoder로 고정하여 사용
  //    Spring Security 6 까지는 prefix가 없어도 기본으로 BCrypt로 처리가 되었었는데,
  //    Spring Security 7부터는 prefix가 필요하다. 그래서 일단 BCryptPasswordEncoder로 고정해서 사용해보자!
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
    // 💡 Spring JDBC에서는 JdbcDaoSupport를 정리할 예정이였는데,
    //     Spring Security의 JdbcTokenRepositoryImpl가 여전히 사용해서
    //     아직 즉시 지워지진 않은 것 같다.
    jdbcTokenRepository.setDataSource(dataSource);
    return jdbcTokenRepository;
  }
}
