package models;

/**
 * Created by mackenzie on 07/08/17.
 * @author mackenzie
 *
 * The person object represents a person, and their personal information,
 * whether they are a customer of recipient of food
 *
 */
public class Person {

    private String firstName;
    private String lastName;
    private String street;
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
     * @param state            The state or province where the person is located
     * @param postal           The postal/zip code of the person's street address
     * @param country          The country in which the person is located
     * @param email            The person's email address
     * @param phone            The person's phone number
     */
    public Person(String firstName, String lastName, String street,
                  String state, String postal, String country, String email,
                  String phone){


    }

    /**
     * Checks whether an email is valid or not
     * @param email
     * @return true if the email is valid, false if it is not
     */
    private boolean checkEmail(String email) {
        return false;
    }

    /**
     * Getter for person firstname
     * @return the person's firstname as a string
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for person's firstname
     * @param firstName     A string containing the person's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

     /**
     * Getter for person's last name
     * @return the person's last name as a string
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *  Setter for person's firstname
     * @param lastName A string containing the person's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for a person's street address
     * @return  street address as a string
     */
    public String getStreet() {
        return street;
    }

    /**
     * Setter for a person's street address
     * @param street    The street of the person's mailing address
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Getter for a person's state or province of residence
     * @return      The state or province as a string
     */
    public String getState() {
        return state;
    }

    /**
     * Setter for a person's state/ province of residence
     * @param state     The state or province as a string
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *  Getter for a person's postal/zip code
     * @return  The postal code as a string
     */
    public String getPostal() {
        return postal;
    }

    /**
     *  Setter for a person's postal/zip code
     * @param postal The postal code as a string
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     * Getter for a person's country of residence
     * @return  The person's country of residence
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter for a person's country of residence
     * @param country   The person's country of residence
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Getter for a person's country of residence
     * @return  The person's country of residence
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for a person's email address
     * @param  email    person's email address as a string
     * @throws  IllegalArgumentException if email is not a valid format
     */
    public void setEmail(String email) {
        if (checkEmail(email)) this.email = email;
        else throw new IllegalArgumentException();
    }

     /**
     * Getter for a person's phone number
     * @return  The person's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for a person's phone number
     * @param phone the person's phone number as a string
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
