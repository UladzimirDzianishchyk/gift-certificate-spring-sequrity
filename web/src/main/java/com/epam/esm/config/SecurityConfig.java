package com.epam.esm.config;

import com.epam.esm.security.JwtConfigurer;
import com.epam.esm.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/auth/reg","/auth/login").permitAll()
                .antMatchers(HttpMethod.GET, "/gift-certificates/**").permitAll()
                .antMatchers(HttpMethod.GET, "/tags/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("user","admin")
                .antMatchers(HttpMethod.GET, "/orders/**").hasAnyAuthority("user","admin")
                .antMatchers(HttpMethod.DELETE).hasAuthority("admin")
                .antMatchers(HttpMethod.POST).hasAuthority("admin")
                .antMatchers(HttpMethod.PATCH).hasAuthority("admin")
                .anyRequest()
                .authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
