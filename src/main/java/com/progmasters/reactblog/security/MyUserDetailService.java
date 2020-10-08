package com.progmasters.reactblog.security;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<User> user = repository.findById(Long.parseLong(s));
        if (user.isPresent()) {
            User AuthUser = user.get();
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(AuthUser.getRole().toString());

            UserDetails principal = org.springframework.security.core.userdetails.User.withUsername(AuthUser.getId().toString())
                    .authorities(authorities).password(AuthUser.getPassword()).build();
            return principal;
        }
        return null;
    }
}
