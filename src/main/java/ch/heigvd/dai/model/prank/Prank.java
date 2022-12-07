package ch.heigvd.dai.model.prank;

import ch.heigvd.dai.model.mail.Message;
import ch.heigvd.dai.model.mail.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Anthony David
 * @author Stéphane Nascimento Santos
 * @version
 */
public class Prank {
    // Constants literals
    private static final String LF = "\n";
    private static final String CRLF = "\r\n";

    private Person sender;
    private final ArrayList<Person> recipients = new ArrayList<>();
    private String message;
    private String subject;
    private String body;

    /**
     * Setter on sneder
     * @param sender new sender
     */
    public void setSender(Person sender) {
        this.sender = sender;
    }

    /**
     * Setter on message
     * @param message new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Setter on the victims
     * @param victims List<Person> of the victims
     */
    public void setRecipiens(List<Person> victims) {
        recipients.addAll(victims);
    }

    /**
     * Generation of a new message corectly formed
     * @return the new message
     */
    public Message generateMessage() {
        Message msg = new Message();

        int indexEndSubject = message.indexOf(LF);
        if (indexEndSubject == -1) indexEndSubject = message.indexOf(CRLF);
        subject = message.substring(0, indexEndSubject).replace("Subject: ", "").trim();
        body = message.substring(indexEndSubject + CRLF.length()).trim();

        msg.setSubject(this.subject + CRLF);
        if (sender.getFirstName() != null) {
            body += (CRLF + sender.getFirstName() + " " + sender.getLastName());
        }
        msg.setBody(body);


        String[] to = recipients.stream().map(p -> p.getAddress()).collect(Collectors.toList()).toArray(new String[]{});
        msg.setTo(to);

        msg.setFrom(sender.getAddress());

        return msg;
    }


}
