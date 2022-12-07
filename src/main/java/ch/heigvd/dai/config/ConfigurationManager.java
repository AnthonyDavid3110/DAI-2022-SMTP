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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Anthony David
 * @author Stéphane Nascimento Santos
 * @version 1.0
 */
public class ConfigurationManager implements IConfigurationManager {
    // Constantes Literals
    private static final String CRLF = "\r\n";
    private static final String MESSAGE_SEPARATOR = "==";
    private static final String REGEX = "^(.+)@(.+)$";

    private String smtpServerAddr;
    private int smtpServerPort;
    private final List<Person> victims;
    private final List<String> messages;
    private int numberOfGroups;

    /**
     * Constructor
     * @throws IOException if problems with the reading of the file
     */
    public ConfigurationManager() throws IOException {
        victims = loadAddressesFromFile("./config/addresses.utf8");
        messages = loadMessagesFromFile("./config/messages.utf8");
        loadProperties("./config/param.properties");
    }

    /**
     * Function to load the parameters of the app in the file param.properties
     * @param fileName path and name of the file param.properties
     * @throws IOException if problems with the reading of the file
     */
    private void loadProperties(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        Properties properties = new Properties();
        properties.load(fis);
        this.smtpServerAddr = properties.getProperty("host");
        this.smtpServerPort = Integer.parseInt(properties.getProperty("port"));
        this.numberOfGroups = Integer.parseInt(properties.getProperty("nbgroups"));
    }

    /**
     * Function to load the adresses in the file adresses.utf8
     * @param fileName path and name of the file adresses.utf8
     * @return List<Person> of all the adress in the file
     * @throws IOException if problems with the reading of the file
     */
    private List<Person> loadAddressesFromFile(String fileName) throws IOException {
        List<Person> result;
        Pattern pattern = Pattern.compile(REGEX);
        try (FileInputStream fis = new FileInputStream(fileName)) {
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(isr)) {
                result = new ArrayList<>();
                String address = reader.readLine();
                while (address != null) {
                    result.add(new Person(address));
                    Matcher matcher = pattern.matcher(address);
                    // Check the format of the mail address
                    if (!matcher.matches()) {
                        throw new IOException("Bad Email format");
                    }
                    address = reader.readLine();
                }
            }
        }
        return result;
    }

    /**
     * Function to load the messages in the file messages.utf8
     * @param fileName poth and name of the file messages.utf8
     * @return List<string> of all the messages in the file
     * @throws IOException if problem with the reading of the file
     */
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

    /**
     * getter of victims
     * @return List<Person> of the victims
     */
    @Override
    public List<Person> getVictims() {
        return victims;
    }

    /**
     * Getter of messages
     * @return List<String> of all the messages
     */
    @Override
    public List<String> getMessages() {
        return messages;
    }

    @Override
    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    /**
     * Getter of the smtpServerAddr
     * @return String with the adress of the SMTP Server parameter in the configuration file
     */
    @Override
    public String getSmtpServerAddr(){
        return smtpServerAddr;
    }

    /**
     * Getter of the smtpServerPort
     * @return int with the port of the SMTP server parameter in the configuration file
     */
    @Override
    public int getSmtpServerPort(){
        return smtpServerPort;
    }
}
