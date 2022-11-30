package src.main.java.ch.heigvd.dai.config;

import src.main.java.ch.heigvd.dai.model.mail.Person;

import java.util.List;

public interface IConfigurationManager {
    List<Person> getVictims();

    List<String> getMessages();

    int getNumberOfGroups();

    String getSmtpServerAddr();

    int getSmtpServerPort();
}
