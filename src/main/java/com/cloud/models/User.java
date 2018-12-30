package com.cloud.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigInteger;

@Document("users")
public class User {
    private @Id
    @GeneratedValue
    BigInteger id;

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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
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
        builder.append("{ 'firstname':'")
                .append(firstName)
                .append("', 'lastname':'")
                .append(lastName)
                .append("', 'birthdate':'")
                .append(birthDay)
                .append("', 'position':")
                .append(position)
                .append(" }");
        return builder.toString();
    }
}
