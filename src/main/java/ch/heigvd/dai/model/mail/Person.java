package ch.heigvd.dai.model.mail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Anthony David
 * @author Stéphane Nascimento Santos
 * @version 1.0
 */
public class Person {
    private String firstName;
    private String lastName;
    private final String address;

    /**
     *  Constructor with parameters
     * @param address mail adress of the Person
     */
    public Person(String address) {
        this.address = address;
        // We try to find the firstname and name of the person if it's possible (firstname.surname@...)
        Pattern pattern = Pattern.compile("(.*)\\.(.*)@");
        Matcher matcher = pattern.matcher(address);
        boolean found = matcher.find();
        if(found) {
            this.firstName = matcher.group(1);
            firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1);
            this.lastName = matcher.group(2);
            lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1);
        }
    }

    /**
     * Gette firstName
     * @return String firstName
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Getter lastName
     * @return String lastName
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Gettet address
     * @return the email address of the Person
     */
    public String getAddress() {
        return address;
    }
}
