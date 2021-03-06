package com.codecool.pionierzy.gotchiarena.model;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @NotEmpty(message = "Please provide username")
    private String username;

    @Column(name = "password")
    @Length(min = 1, message = "Your password should be at least 1 characters long")
    private String password;

    @Column(name = "gotchi_list", columnDefinition = "VARCHAR(255)")
    private String gotchiList;

    @Transient
    private String confirmPassword;


    public User() {
    }

    public User(String username) {
        this.username = username;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        if (!other.username.equals(this.username)) return false;
        return other.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    public ArrayList<Long> getGotchiList() {
        if (StringUtils.isBlank(gotchiList))
            return new ArrayList<>();
        ArrayList<Long> result = new ArrayList<Long>();
        //System.out.println();
        for (String s : gotchiList.split(",")) {
            result.add(Long.valueOf(s));
        }
        return result;
    }

    public void setGotchiList(ArrayList<Long> gotchiList) {
        this.gotchiList = StringUtils.join(gotchiList, ",");
    }
}
