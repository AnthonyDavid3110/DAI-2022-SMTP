package src.main.java.ch.heigvd.dai.config;

import src.main.java.ch.heigvd.dai.model.mail.Person;

import java.util.List;

public interface IConfigurationManager {
    public List<Person> getVictims();

    public List<String> getMessages();

    public int getNumberOfGroups();
}
