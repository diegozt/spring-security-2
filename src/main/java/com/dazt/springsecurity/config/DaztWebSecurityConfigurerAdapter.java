package com.dazt.springsecurity.config;

import com.dazt.springsecurity.config.filter.*;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class DaztWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    /**
     * Este metodo nos permite configurar los diferentes tipos de accesos a los endpoints
     * de acuerdo a si el request entrante esta autenticado o no
     * /myAccount: secured
     * /myBalances: secured
     * /myCards: secured
     * /contact: not secured
     * /myLoans: secured
     * /notices: not secured
     * @param http: http security parameter
     * @throws Exception: exception returned from this method
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .cors().configurationSource(new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest httpServletRequest) {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfiguration.setExposedHeaders(List.of("Authorization"));
                    corsConfiguration.setMaxAge(3600L);//seconds
                    return corsConfiguration;
                }
            })
            .and()
            .csrf().disable()//CSRF is managed internally by the JWT as per it is a token aswell
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.ignoringAntMatchers("/contact")
                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new RequestValidationAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/myAccount").hasAuthority("UPDATE")
                .antMatchers("/myCards").hasRole("USER")
                .antMatchers("/myBalances").hasRole("ADMIN")
                .antMatchers("/myLoans").authenticated()
                .antMatchers("/notices").permitAll()
                .antMatchers("/contact").permitAll()
            .and()
                .formLogin()
            .and()
                .httpBasic();
    }

    /**
     * Method to configure the UserDetails and the password encoder if we want to customize it
     * This implementation is for manage users inMemoryAuthentication
     * @param auth: authentication manager builds
     * @throws Exception: exception thrown by this method
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("12345").authorities("admin")
                .and()
                .withUser("user").password("12345").authorities("read")
                .and()
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
    */

    /**
     * This is another way how to add our users, saving their data in Spring context memory
     * @param auth: authentication manager builds
     * @throws Exception: exception thrown by this method
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManager userDetailService = new InMemoryUserDetailsManager();
        UserDetails user = User.builder().username("diegozt").password("12345").authorities("admin").build();
        UserDetails user1 = User.builder().username("lena").password("12345").authorities("read").build();
        userDetailService.createUser(user);
        userDetailService.createUser(user1);
        auth.userDetailsService(userDetailService);
    }
     */

    /**
     * Method which helps you to configure the authentication manager by DB
     * @param dataSource: DataSource object to connect DB.
    @Bean
    public UserDetailsService userDetailsServiceBean(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
     */

    /**
     * If by default we do not have a password Encoder, Spring Security will take this bean
     * @return returned method
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
