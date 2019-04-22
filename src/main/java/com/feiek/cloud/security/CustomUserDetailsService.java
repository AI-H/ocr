package com.feiek.cloud.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class CustomUserDetailsService implements UserDetailsService{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if("wangheng".equals(username)){
            return new SecurityUser("wangheng","wangheng","admin");
        }else if("caoliyang".equals(username)){
            return new SecurityUser("caoliyang","caoliyang","admin");
        }else{
            return null;
        }
    }
}
