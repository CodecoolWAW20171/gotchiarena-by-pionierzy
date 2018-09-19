package com.codecool.pionierzy.gotchiarena.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @NotEmpty(message = "Please provide username")
    private String username;

    @Column(name = "password")
    @Length(min = 5, message = "Your password should be at least 5 characters long")
    private String password;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
