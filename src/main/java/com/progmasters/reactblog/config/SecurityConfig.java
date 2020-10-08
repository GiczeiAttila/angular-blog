package com.progmasters.reactblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progmasters.reactblog.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${cors-policies}")
    private String[] corsPolicies;

    private static final String SECRET_PROPERTY_NAME = "security.jwt.secret";
    private Environment environment;
    private MyUserDetailService userDetailService;

    @Autowired
    public SecurityConfig(Environment environment, MyUserDetailService userDetailService) {
        this.environment = environment;
        this.userDetailService = userDetailService;
    }

    private static void handleException(HttpServletRequest req, HttpServletResponse rsp, AuthenticationException e)
            throws IOException {
        PrintWriter writer = rsp.getWriter();
        writer.println(new ObjectMapper().writeValueAsString(new AuthorizationResponse("error", "Unauthorized")));
        rsp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String secret = environment.getProperty(SECRET_PROPERTY_NAME);
        JwtCookieStore jwtCookieStore = new JwtCookieStore(secret.getBytes());
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(SecurityConfig::handleException)
                .and().authorizeRequests()
                .antMatchers("/api/post").hasAnyRole("USER", "MANAGER", "HR", "ADMIN")
                .antMatchers("/api/post/**").hasAnyRole("USER", "MANAGER", "HR", "ADMIN")
              //  .antMatchers("/api/registration").hasAnyRole("HR", "ADMIN")
                .antMatchers("/api/users/create").hasAnyRole("HR", "ADMIN")
                .antMatchers("/api/meetingRoom-form").hasAnyRole( "HR", "ADMIN")
                .antMatchers("/api/meetingReservation").hasAnyRole("USER", "MANAGER", "HR", "ADMIN")
                .antMatchers("/api/timeOff-list").hasAnyRole("MANAGER", "HR", "ADMIN")
                .antMatchers("/api/suggestion-box").hasAnyRole("USER", "MANAGER", "HR", "ADMIN")
                .antMatchers("/api/open-position").hasAnyRole("USER", "MANAGER", "HR", "ADMIN")
                .antMatchers("/api/postForm").hasAnyRole("USER", "MANAGER", "HR", "ADMIN")
                .antMatchers("/api/postForm").hasAnyRole("USER", "MANAGER", "HR", "ADMIN")
                .antMatchers("/api/fullcalendar").hasAnyRole("USER", "MANAGER", "HR", "ADMIN")
                .antMatchers("/api/timeOffForm").hasAnyRole("USER", "MANAGER", "HR", "ADMIN")
                .antMatchers("/api/meeting-list").hasAnyRole("USER", "MANAGER", "HR", "ADMIN")

                //  .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(jwtCookieStore, authenticationManager()))
                //.addFilterAfter(new JwtTokenAuthenticationFilter(jwtCookieStore), UsernamePasswordAuthenticationFilter.class)
                //.authorizeRequests()
              //  .antMatchers(HttpMethod.POST, "/api/welcome", "/api/login", "/api/registration", "/api/users/create", "/api/users/login", "/api/logout").permitAll()
                //.antMatchers(HttpMethod.GET, "/api/welcome", "/api/login", "/api/registration", "/api/users/create")
              //  .permitAll()
                .antMatchers("/**").permitAll()
               // .hasAnyRole("USER", "MANAGER", "HR", "ADMIN").permitAl0
                .anyRequest().authenticated().and().logout().deleteCookies().and().httpBasic();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(corsPolicies));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-Requested-With","Access-Control-Allow-Origin: *"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
}
