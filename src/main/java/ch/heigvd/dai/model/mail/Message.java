package ch.heigvd.dai.model.mail;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Anthony David
 * @author Stéphane Nascimento Santos
 * @version 1.0
 */
public class Message {
    private String from;
    private String[] to = new String[0];
    private String subject;
    private String body;

    /**
     * Getter of from
     * @return String of the "from:" part of the message
     */
    public String getFrom() {
        return from;
    }

    /**
     * Setter on from
     * @param from new from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Getter of to
     * @return String[] with all the "to:" part in the mail
     */
    public String[] getTo(){
        return to;
    }

    /**
     * Setter of to
     * @param to new to
     */
    public void setTo(String[] to) {
        this.to = to;
    }

    /**
     * Getter on the subject
     * @return String of the "Subject:" part of the mail
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * Getter on the subject encoded in Base64
     * @return String of the "Subject:" part of the mail encoded in Base64
     */
    public String getBase64Subject() {
        return new String(Base64.getEncoder().encode(subject.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Setter of the subject
     * @param subject new subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Getter of Body
     * @return String "Body" part of the mail
     */
    public String getBody()
    {
        return body;
    }

    /**
     * Setter on body
     * @param body new body
     */
    public void setBody(String body) {
        this.body = body;
    }

}
