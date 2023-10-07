package com.dan.chatop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class User {
    @Id
    private Long id;

    private String name;

    private String email;

    private String password;

    private Date createdAt;

    private Date updateAt;



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
