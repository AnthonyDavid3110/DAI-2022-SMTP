package ch.heigvd.dai.config;

import ch.heigvd.dai.model.mail.Person;

import java.util.List;

public interface IConfigurationManager {
    public List<Person> getVictims();

    public List<String> getMessages();

    public int getNumberOfGroups();

    public String getSmtpServerAddr();

    public int getSmtpServerPort();
}
