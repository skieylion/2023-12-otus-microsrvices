package com.example.auth.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String roles;

    public void setRoles(Set<String> roles) {
        this.roles = String.join(",", roles);
    }

    public Set<? extends GrantedAuthority> getRoles() {
        return Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
