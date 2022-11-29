package src.main.java.ch.heigvd.dai.model.mail;

public class Message {
    private final String subject;
    private final String body;

    public Message(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public String getSubject()
    {
        return subject;
    }


    public String getBody()
    {
        return body;
    }


    public String toSring()
    {
        return "Message{subject='" + subject + "'body='" + body + "}";
    }
}
