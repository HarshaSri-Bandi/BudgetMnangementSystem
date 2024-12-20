package com.example.UserService.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.UserService.Entity.CustomerEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



public class CustomerDetailsInfo implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String email;
    private String password;

    private List<GrantedAuthority> authorities;

    public CustomerDetailsInfo(CustomerEntity customerInfo) {
        email = customerInfo.getEmail();
        password = customerInfo.getPassword();
        authorities = Arrays.stream(customerInfo.getRoles().split(",")).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
