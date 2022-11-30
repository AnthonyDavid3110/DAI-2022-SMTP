package src.main.java.ch.heigvd.dai.model.prank;

import src.main.java.ch.heigvd.dai.model.mail.Message;
import src.main.java.ch.heigvd.dai.model.mail.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Prank {
    private static final String LF = "\n";
    private static final String CRLF = "\r\n";
    private Person sender;
    private final ArrayList<Person> recipients = new ArrayList<>();
    private String message;

    public Person getVictimSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addRecipiens(List<Person> victims) {
        recipients.addAll(victims);
    }

    public List<Person> getRecipiens() {
        return recipients;
    }

    public Message generateMessage() {
        Message msg = new Message();

        msg.setBody(this.message + CRLF + sender.getFirstName());

        String[] to = recipients.stream().map(p -> p.getAddress()).collect(Collectors.toList()).toArray(new String[]{});
        msg.setTo(to);

        msg.setFrom(sender.getAddress());

        return msg;
    }


}
