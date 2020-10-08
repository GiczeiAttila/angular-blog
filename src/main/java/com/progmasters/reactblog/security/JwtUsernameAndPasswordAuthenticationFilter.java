package com.progmasters.reactblog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progmasters.reactblog.domain.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtCookieStore jwtCookieStore;

    private AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordAuthenticationFilter(JwtCookieStore jwtCookieStore, AuthenticationManager authenticationManager) {
        this.jwtCookieStore = jwtCookieStore;
        this.authenticationManager = authenticationManager;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)  {
        User credentials = readUserCredentials(request);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    credentials.getId(), credentials.getPassword(), Collections.emptyList());

            return authenticationManager.authenticate(authToken);

    }

    private User readUserCredentials(HttpServletRequest request) {
        try {
            return new ObjectMapper().readValue(request.getInputStream(), User.class);
        }
        catch (IOException ioe) {
            throw new BadCredentialsException("Invalid request", ioe);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException {
        jwtCookieStore.storeToken(response, auth);

        writeResponse(response);
    }

    private void writeResponse(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println(new ObjectMapper().writeValueAsString(new AuthorizationResponse("ok", "Successful authentication")));
    }

}
