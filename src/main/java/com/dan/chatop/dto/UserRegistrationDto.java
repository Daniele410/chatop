package com.dan.chatop.dto;

import java.util.Date;


public class UserRegistrationDto {

    private long id;
    private String email;
    private String name;
    private String password;
    private Date createdAt;
    private Date updatedAt;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public UserRegistrationDto(long id, String email, String name, Date createdAt, Date updatedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
