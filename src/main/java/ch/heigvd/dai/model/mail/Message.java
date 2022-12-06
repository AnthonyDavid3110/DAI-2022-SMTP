package ch.heigvd.dai.model.mail;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Message {
    private String from;
    private String[] to = new String[0];
    private String subject;
    private String body;


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo(){
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getBase64Subject() {
        return new String(Base64.getEncoder().encode(subject.getBytes(StandardCharsets.UTF_8)));
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
