package ch.heigvd.dai.smtp;

import ch.heigvd.dai.model.mail.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * @author Anthony David
 * @author Stéphane Nascimento Santos
 */
public class SmtpClient implements ISmtpClient {
    private static final String CRLF = "\r\n";
    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private final String smtpServerAddr;
    private final int smtpServerPort;

    /**
     * Constructor
     * @param smtpServerAddr SMTP adress
     * @param smtpServerPort SMTP port
     */
    public SmtpClient(String smtpServerAddr, int smtpServerPort) {
        this.smtpServerAddr = smtpServerAddr;
        this.smtpServerPort = smtpServerPort;
    }

    /**
     * Connection end sending of one message to the SMTP server
     * @param message
     * @throws IOException
     */
    @Override
    public void sendMessage(Message message) throws IOException {
        LOG.info("Sending message via SMTP");

        Socket socket = new Socket(smtpServerAddr, smtpServerPort);
        PrintWriter writer = new PrintWriter((new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

        String line = reader.readLine();
        LOG.info(line);

        writer.printf("EHLO localhost" + CRLF);
        line = reader.readLine();
        LOG.info(line);

        if (!line.startsWith("250")) {
            throw new IOException("SMTP error: " + line);
        }
        while(line.startsWith("250-")){
            line = reader.readLine();
            LOG.info(line);
        }

        writer.write("MAIL FROM:");
        writer.write(message.getFrom());
        writer.write(CRLF);
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        for (String to : message.getTo()){
            writer.write("RCPT TO:");
            writer.write(to);
            writer.write(CRLF);
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }

        writer.write("DATA");
        writer.write(CRLF);
        writer.flush();

        line = reader.readLine();
        LOG.info(line);

        writer.write("Content-Type: text/plain; charset=utf-8" + CRLF);
        writer.write("From: " + message.getFrom() + CRLF);
        writer.write("To: " + message.getTo()[0]);

        for (int i = 1; i < message.getTo().length; i++){
            writer.write(", " + message.getTo()[i]);
        }
        writer.write(CRLF);
        writer.flush();

        writer.write("Subject: =?utf-8?B?" + message.getBase64Subject() + "?=" + CRLF);
        writer.write(message.getBody());
        writer.write(CRLF);
        writer.write(".");
        writer.write(CRLF);
        writer.flush();

        line = reader.readLine();

        LOG.info(line);
        writer.write("QUIT" + CRLF);
        writer.flush();
        reader.close();
        writer.close();
        socket.close();
    }
}
