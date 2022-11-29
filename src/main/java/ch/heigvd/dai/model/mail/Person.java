package src.main.java.ch.heigvd.dai.model.mail;

public class Person {
    private final String email;

    public Person(String email)
    {
        // Regex on email adress
        if (!email.matches("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)"))
            throw new IllegalArgumentException("'" + email + "' is an invalid email !");
        this.email = email;
    }

    public String getEmailAdress() {
        return email;
    }

    public String toString()
    {
        return "User{email='" + email + "'}";
    }
}
