package com.example.cardealer.security;

import com.example.cardealer.users.control.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    public ApplicationSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("**/assets/**").permitAll()
                .antMatchers("**/js/**").permitAll()
                .antMatchers("**/css/**").permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers("/users/**").authenticated()
                .antMatchers("/customers/**").authenticated()
                .antMatchers("/cars/**").authenticated()
                .antMatchers("/repairs/**").authenticated()
                .antMatchers("/invoices/**").authenticated()
                .antMatchers("/agreements/**").authenticated()
                .antMatchers("/cessions/**").authenticated()
                .antMatchers("/reports/**").authenticated()
                .antMatchers("/employees/**").authenticated()
                .and().formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .and().httpBasic()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();
    }

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }*/

    /*protected void configure(HttpSecurity http)*/
    /* http.cors().disable();*/
    /* http*/
    /* .authorizeRequests()*/
    /* .antMatchers("/css/**,/assets/**,/js/**").permitAll()*/
    /* .antMatchers("**//*").permitAll()
                .antMatchers("**//*index").permitAll()
                .antMatchers("/sale/**").hasAnyRole("CLIENT", "ADMIN")
                .antMatchers("/owner/**").hasAnyRole("CLIENT", "ADMIN")
                .antMatchers("/worker/**").hasAnyRole("WORKER", "TRADER", "MANAGER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/owner")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(
                        (req,res,auth)-> res.sendRedirect("/owner")
                )
                .failureHandler((req,res,exp)->{
                    String errorMessage;
                    if(exp.getClass().isAssignableFrom(BadCredentialsException.class)){
                        errorMessage = "Invalid username or password";
                    }else {
                        errorMessage = "unkonow error " + exp.getMessage();
                    }
                    req.getSession().setAttribute("message", errorMessage);
                    res.sendRedirect("/login");
                })
                .permitAll()
                .and().logout().logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler((req,res,auth)->{
                    res.sendRedirect("/login");
                })
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/login")
                .and()
                .csrf().disable();
        http.headers().frameOptions().disable();*/


}
