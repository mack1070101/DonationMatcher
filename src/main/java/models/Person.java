package models;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by mackenzie on 07/08/17.
 * @author mackenzie
 *
 * The person object represents a person, and their personal information,
 * whether they are a customer of recipient of food
 *
 * TODO future validate data better, including empty fields
 * TODO perhaps tear out almost all getters and setters
 */
public class Person {

    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String state;
    private String postal;
    private String country;
    private String email;
    private String phone;

    /**
     * Constructor for the person class
     * @param firstName        A person's first name
     * @param lastName         A person's last name
     * @param street           The street address where the person is located
     * @param city             The city where the person is located
     * @param state            The state or province where the person is located
     * @param postal           The postal/zip code of the person's street address
     * @param country          The country in which the person is located
     * @param email            The person's email address
     * @param phone            The person's phone number
     *
     * @throws IllegalArgumentException in the event of a bad argument
     */
    public Person(String firstName, String lastName, String street,
                  String city, String state, String postal, String country, String email,
                  String phone){


        boolean emailValidity = checkEmail(email);
        if(!emailValidity) throw new IllegalArgumentException();

        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postal = postal;
        this.country = country;
        this.email = email;
        this.phone = phone;

    }

    /**
     * Checks whether an email is valid or not
     * @param email string representation of the email to be checked
     * @return true if the email is valid, false if it is not
     */
    public boolean checkEmail(String email) {
        boolean result = true;

        try{
            InternetAddress e = new InternetAddress(email);
            e.validate();
        } catch (AddressException e) {
            result = false;
        }

        return result;
    }

    public String getPhone() {
        return phone;
    }

    public String toCsv(){
        String sb = firstName +
                "," +
                lastName +
                "," +
                street +
                "," +
                city +
                "," +
                state +
                "," +
                postal +
                "," +
                country +
                "," +
                email +
                "," +
                phone;

        return sb;
    }
}


