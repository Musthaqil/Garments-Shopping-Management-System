package com.example.readymadeShop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// src/main/java/com/example/readymadeShop/entity/User.java

@Document(collection = "SignUpDetails")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String userType;
    private String verificationToken;
    private boolean verified;

    public User(){
        
    }

    //getters and setters
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }

    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public String getUserType(){
        return userType;
    }
    public void setUserType(String userType){
        this.userType=userType;
    }

    public String getVerificationToken(){
        return verificationToken;
    }
    public void setVerificationToken(String verificationToken){
        this.verificationToken=verificationToken;
    }

    public boolean isVerified(){
        return verified;
    }
    public void setVerified(boolean verified){
        this.verified = verified;
    }

}
