package com.example.customerfetchapi;

public class Customer {
    private String name;
    private String username;
    private String email;
    private Integer Id;
    private Object address;
    private Object avatar;


    public Customer(Integer Id,String name,String username,String email, Object address, Object avatar ) {
        this.name = name;
        this.Id = Id;
        this.username = username;
        this.email = email;
        this.address = address;
        this.avatar = avatar;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Object getAddress() {
        return this.address;
    }

    public Object getAvatar() {
        return this.avatar;
    }

    public String getEmail() {
        return this.email;
    }

    public Integer getId() {
        return this.Id;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }
}
