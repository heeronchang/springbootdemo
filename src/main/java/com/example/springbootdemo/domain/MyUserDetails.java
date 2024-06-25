package com.example.springbootdemo.domain;

import com.example.springbootdemo.domain.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
public class MyUserDetails implements UserDetails {
    private Long id;
    private String password;
    private String username;
    private List<GrantedAuthority> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    public MyUserDetails(User user) {
        this.id = user.getId();
        this.password = user.getPassword();
        this.username = user.getUsername();
    }

    public MyUserDetails(User user, List<GrantedAuthority> authorities) {
        this.id = user.getId();
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.authorities = authorities;
    }
}
