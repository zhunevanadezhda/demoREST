package com.example.demo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class User {
    @Positive
    private int id;
    @Length(max=35)
    private String surname;
    @Length(max=30)
    private String name;
    @Length(max=30)
    private String patronym;
    @NotNull
    @Length(max=25)
    private String login;
    @NotNull
    private String password;
    @NotNull
    @PastOrPresent
    private LocalDateTime registration;
    private char gender;
    @NotNull
    @Positive
    private short role=1;

    public User() {
        super();
    }

    public User(int id, String surname, String name, String patronym, String login, String password, LocalDateTime registration, char gender, short role) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronym = patronym;
        this.login = login;
        this.password = password;
        this.registration = registration;
        this.gender = gender;
        this.role = role;
    }

    public User(String login, String password, LocalDateTime registration, short role) {
        this.login = login;
        this.password = password;
        this.registration = registration;
        this.role = role;
    }

    public User(String surname, String name, String patronym, String login, String password, char gender, short role) {
        this.surname = surname;
        this.name = name;
        this.patronym = patronym;
        this.login = login;
        this.password = password;
        this.gender = gender;
        this.role = role;
    }

    public void write()
    {
        System.out.println("id: "+id+"; surname: "+ surname+"; name: "+name+"; patronym: "+patronym+"; login: "+login+"; password: "+password+"; registration: "+registration+"; gender="+gender+"; role="+role);
    }

    public User(int id, String surname, String login, String password, LocalDateTime registration) {
        this.id = id;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.registration = registration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronym() {
        return patronym;
    }

    public void setPatronym(String patronym) {
        this.patronym = patronym;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegistration() {
        return registration;
    }

    public void setRegistration(LocalDateTime registration) {
        this.registration = registration;
    }

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

}
