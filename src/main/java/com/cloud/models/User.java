package com.cloud.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Document("users")
public class User {
    private @Id
    @GeneratedValue
    String id;

    private String firstName;
    private String lastName;
    private Position position;
    private String birthDay;


    public User(String firstName, String lastName, Position position, String birthDay) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.birthDay = birthDay;
    }

    public User() {
        this.firstName = "John";
        this.lastName = "Doe";
        this.position = new Position();
        this.birthDay = "01/01/2000";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    @JsonProperty("birthDay")
    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public Position getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"id\":\"")
                .append(id)
                .append("\",\"firstName\":\"")
                .append(firstName)
                .append("\",\"lastName\":\"")
                .append(lastName)
                .append("\",\"position\":")
                .append(position)
                .append(",\"birthDay\":\"")
                .append(birthDay)
                .append("\"}");
        return builder.toString();
    }
}
