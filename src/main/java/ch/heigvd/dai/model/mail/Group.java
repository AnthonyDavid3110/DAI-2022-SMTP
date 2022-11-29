package src.main.java.ch.heigvd.dai.model.mail;

import java.util.ArrayList;

public class Group {
    private final Person sender;
    private final ArrayList<Person> recipients;

    public Group(Person sender, ArrayList<Person> recipients) throws IllegalArgumentException
    {
        if (recipients.size() < 2) throw new IllegalArgumentException("Groupe must contains at least 2 recipients !");
        this.sender = sender;
        this.recipients = recipients;
    }

    public Person getSender()
    {
        return sender;
    }

    public ArrayList<Person> getRecipients()
    {
       return recipients;
    }

    public String toStrinf()
    {
        return "Groupe{sender='" + sender + "', recipients='" + recipients + "'}";
    }
}
