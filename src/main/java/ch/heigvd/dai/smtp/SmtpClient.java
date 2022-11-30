package src.main.java.ch.heigvd.dai.smtp;

import src.main.java.ch.heigvd.dai.model.mail.Message;

import java.io.*;
import java.net.Socket;
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

    private Socket socket;

    public SmtpClient(String smtpServerAddr, int smtpServerPort) {
        this.smtpServerAddr = smtpServerAddr;
        this.smtpServerPort = smtpServerPort;
    }

    @Override
    public void sendMessage(Message message) throws IOException {
        LOG.info("Sending message via SMTP");

        Socket socket = new Socket(smtpServerAddr, smtpServerPort);

        PrintWriter writer = new PrintWriter((new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

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
        writer.write("Content.Tye: text/plain; charset=\"utf-8\"" + CRLF);
        writer.write("From: " + message.getFrom() + CRLF);

        writer.write("To: " + message.getTo()[0] + CRLF);
        for (int i = 1; i < message.getTo().length; i++){
            writer.write(", " + message.getTo()[i]);
        }
        writer.write(CRLF);

        writer.flush();

        LOG.info(message.getBody());
        writer.write(message.getFrom());
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
