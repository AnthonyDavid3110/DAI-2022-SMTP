package ch.heigvd.dai.smtp;

import ch.heigvd.dai.model.mail.Message;

import java.io.IOException;

/**
 * @author Anthony David
 * @author Stéphane Nascimento Santos
 */
public interface ISmtpClient {
    void sendMessage(Message message) throws IOException;
}
