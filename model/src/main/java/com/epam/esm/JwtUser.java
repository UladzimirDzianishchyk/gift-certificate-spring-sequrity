package com.epam.esm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private final Long id;
    private final String userName;
    private final String password;
    private final boolean isActive;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(
            Long id,
            String userName,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            boolean isActive
    ) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
        this.isActive= isActive;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
