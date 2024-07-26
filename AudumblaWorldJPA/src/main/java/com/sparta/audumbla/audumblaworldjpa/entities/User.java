package com.sparta.audumbla.audumblaworldjpa.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "users", schema = "library")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true)
    private String username;
    private String password;
    private String roles;

    public User() {
    }
    public User(String userName, String password, String roles) {
        this.username = userName;
        this.password = password;
        this.roles = roles;
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
    public String getRoles() {
        return roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
