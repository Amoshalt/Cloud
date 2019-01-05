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


    /**
     * Create an user from its informations
     * @param firstName of the user
     * @param lastName of the user
     * @param position of the user right now
     * @param birthDay of the user
     */
    public User(String firstName, String lastName, Position position, String birthDay) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.birthDay = birthDay;
    }

    /**
     * Default constructor
     * John Doe, at (0,0), born the 01/01/2000
     */
    public User() {
        this.firstName = "John";
        this.lastName = "Doe";
        this.position = new Position();
        this.birthDay = "01/01/2000";
    }

    /**
     * Getter
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter
     * @param id of the user
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter
     * @param firstName of the user
     */
    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter
     * @param lastName of the user
     */
    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter
     * @return the day of birth
     */
    public String getBirthDay() {
        return birthDay;
    }

    /**
     * Setter
     * @param birthDay of the user
     */
    @JsonProperty("birthDay")
    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * Getter
     * @return the current position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setter
     * @param position of the user
     */
    @JsonProperty("position")
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Convert user to string
     * @return JSON representation of the user
     */
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
