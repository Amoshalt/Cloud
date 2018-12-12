package com.cloud.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class User {
    private @Id
    @GeneratedValue
    Long id;

    private String firstName;
    private String lastName;
    private String birthDay;
    private Position position;


    public User(String firstName, String lastName, String birthDay, Position position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
