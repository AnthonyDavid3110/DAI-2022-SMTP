package ch.heigvd.dai.model.prank;

import ch.heigvd.dai.model.mail.Message;
import ch.heigvd.dai.model.mail.Person;

import java.security.cert.CRL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Prank {
    private static final String LF = "\n";
    private static final String CRLF = "\r\n";
    private Person sender;
    private final ArrayList<Person> recipients = new ArrayList<>();
    private String message;
    private String subject;
    private String body;

    public Person getSender() {
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

        int indexEndSubject = message.indexOf(LF);
        if (indexEndSubject == -1) indexEndSubject = message.indexOf(CRLF);
        subject = message.substring(0, indexEndSubject).replace("Subject: ", "").trim();
        body = message.substring(indexEndSubject + CRLF.length()).trim();

        msg.setSubject(this.subject + CRLF);
        msg.setBody(this.body + CRLF + sender.getFirstName() + " " + sender.getLastName());

        String[] to = recipients.stream().map(p -> p.getAddress()).collect(Collectors.toList()).toArray(new String[]{});
        msg.setTo(to);

        msg.setFrom(sender.getAddress());

        return msg;
    }


}
