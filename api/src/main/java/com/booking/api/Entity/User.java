package com.booking.api.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String country;
    private double credit;

    // Example: A simple list of roles
    @ElementCollection(fetch = FetchType.EAGER)
    private Collection<String> roles;

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    }

    // Implement the methods from UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert roles to GrantedAuthority objects
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }


}
