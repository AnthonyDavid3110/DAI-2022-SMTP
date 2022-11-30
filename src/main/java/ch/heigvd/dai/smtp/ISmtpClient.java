package src.main.java.ch.heigvd.dai.smtp;

import src.main.java.ch.heigvd.dai.model.mail.Message;

import java.io.IOException;

/**
 * @author Anthony David
 * @author Stéphane Nascimento Santos
 */
public interface ISmtpClient {
    public void sendMessage(Message message) throws IOException;
}
