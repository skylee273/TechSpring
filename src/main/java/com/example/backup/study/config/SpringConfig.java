package com.example.backup.study.config;

import com.example.backup.study.repository.BoardRepository;
import com.example.backup.study.repository.MemberRepository;
import com.example.backup.study.service.BoardService;
import com.example.backup.study.service.MemberService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SpringConfig extends WebSecurityConfigurerAdapter {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
    @Bean
    public BoardService boardService(){
        return new BoardService(boardRepository);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new AppAuthenticationSuccessHandler();
    }
    public class AppAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        protected void handle(HttpServletRequest request, HttpServletResponse response,
                              Authentication authentication) throws IOException, ServletException {
        }

    }
    @Override
    public void configure(WebSecurity web) throws Exception
    {
//        web.ignoring().regexMatchers(String.valueOf(PathRequest.toStaticResources().atCommonLocations()));
        web.ignoring().antMatchers("/css/**", "/dist/**", "/scripts/**", "/src/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
       httpSecurity.authorizeRequests()
               .antMatchers("/admin/**").hasRole("ADMIN")
               .antMatchers("/members/login", "/members/register").permitAll() // 누구나 접근 허용
               .antMatchers("/").hasRole("MEMBER")
               .and()
               .formLogin()
               .loginPage("/members/login")
               .successHandler(new AppAuthenticationSuccessHandler())
               .defaultSuccessUrl("/",true)
               .usernameParameter("email")
               .passwordParameter("passWord")
               .permitAll()
               .and()
               .exceptionHandling().accessDeniedPage("/members/denied");
}

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService()).passwordEncoder(passwordEncoder());
    }
}