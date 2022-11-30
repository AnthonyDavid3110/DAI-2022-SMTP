package ch.heigvd.dai.config;

import ch.heigvd.dai.model.mail.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigurationManager implements IConfigurationManager {
    private static final String CRLF = "\r\n";
    private static final String MESSAGE_SEPARATOR = "==";
    private String smtpServerAddr;
    private int smtpServerPort;
    private final List<Person> victims;
    private final List<String> messages;
    private int numberOfGroups;

    public ConfigurationManager() throws IOException {
        victims = loadAddressesFromFile("./config/addresses.utf8");
        messages = loadMessagesFromFile("./config/messages.utf8");
        loadProperties("./config/param.properties");
    }

    private void loadProperties(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        Properties properties = new Properties();
        properties.load(fis);
        this.smtpServerAddr = properties.getProperty("host");
        this.smtpServerPort = Integer.parseInt(properties.getProperty("port"));
        this.numberOfGroups = Integer.parseInt(properties.getProperty("nbgroups"));
    }

    private List<Person> loadAddressesFromFile(String fileName) throws IOException {
        List<Person> result;
        try (FileInputStream fis = new FileInputStream(fileName)) {
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(isr)) {
                result = new ArrayList<>();
                String address = reader.readLine();
                while (address != null) {
                    result.add(new Person(address));
                    address = reader.readLine();
                }
            }
        }
        return result;
    }

    private List<String> loadMessagesFromFile(String fileName) throws IOException {
        List<String> result;
        try (FileInputStream fis = new FileInputStream(fileName)) {
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(isr)) {
                result = new ArrayList<>();
                String line = reader.readLine();
                while (line != null) {
                    StringBuilder body = new StringBuilder();
                    while ((line != null) && !line.equals(MESSAGE_SEPARATOR)) {
                        body.append(line);
                        body.append(CRLF);
                        line = reader.readLine();
                    }
                    result.add(body.toString());
                    line = reader.readLine();
                }
            }
        }
        return result;
    }

    @Override
    public List<Person> getVictims() {
        return victims;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    @Override
    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    @Override
    public String getSmtpServerAddr(){
        return smtpServerAddr;
    }

    @Override
    public int getSmtpServerPort(){
        return smtpServerPort;
    }
}
