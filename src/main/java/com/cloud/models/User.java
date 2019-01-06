package com.cloud.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Document("users")
public class User {
    private @Id
    @GeneratedValue
    String id;

    private String firstName;
    private String lastName;
    private Position position;
    private Date birthDay;
    private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    private Date DateFromString(String str) {
        System.out.println("Date : " + str);
        try {
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


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
        this.birthDay = DateFromString(birthDay);
    }

    /**
     * Default constructor
     * John Doe, at (0,0), born the 01/01/2000
     */
    public User() {
        this.firstName = "John";
        this.lastName = "Doe";
        this.position = new Position();
        this.birthDay = DateFromString("01/01/2000");
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User)o;
        return (user.getId().equals(id))
                && (user.getFirstName().equals(firstName))
                && (user.getLastName().equals(lastName));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result
                + 43 * getId().hashCode()
                + 61 * getLastName().hashCode()
                + 93 * getFirstName().hashCode();
        return result;
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
        String str = format.format(birthDay);
        System.out.println("Format : " + str);
        return str;
    }

    /**
     * Setter
     * @param birthDay of the user
     */
    @JsonProperty("birthDay")
    public void setBirthDay(String birthDay) {
        this.birthDay = DateFromString(birthDay);
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
                .append(getBirthDay())
                .append("\"}");
        return builder.toString();
    }
}
