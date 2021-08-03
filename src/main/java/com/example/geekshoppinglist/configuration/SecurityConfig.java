package com.example.geekshoppinglist.configuration;

import com.example.geekshoppinglist.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder; //отвечает за шифрование паролей

    private UserAuthService userAuthService; //отвечает за проверку пользователей

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserAuthService(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Bean   //проверяет есть ли пользователь в базе
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userAuthService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override //как защищать приложение
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll() //открываем всем доступ к странице login
                .antMatchers("/register").permitAll() //открываем всем доступ к сранице register
                .antMatchers("/**").authenticated() //открываем доступ к остальным страницам только авторизованным юзерам
                .and()
                .formLogin()
                .loginPage("/login") //название страницы логина
                .loginProcessingUrl("/authenticateTheUser") //адрес на который отправляем запрос на проверку логина и пароля пользователя
                .and()
                .logout()
                .logoutSuccessUrl("/login") // переходим сюда после логаута
                .permitAll();
    }
}
