package com.feiek.cloud.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


/**
 * 提供一个用户
 */
public class SecurityUser implements UserDetails {
    private static final long serialVersionUID = -428084025238635770L;

    private Long id;
    private String username;
    private String password;
    private String role;

    public SecurityUser(String username, String password, String role){
        this.username=username;
        this.password=password;
        this.role=role;
    }

    public SecurityUser(){}



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
