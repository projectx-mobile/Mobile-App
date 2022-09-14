package com.jungeeks.email.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "usr")
@Data
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sub")
    private String sub;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;


    public User(String sub, String email, String name) {
        this.sub = sub;
        this.email = email;
        this.name = name;
    }

    public User() {
    }
}
