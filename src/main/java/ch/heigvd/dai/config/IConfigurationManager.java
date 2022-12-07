package ch.heigvd.dai.config;

import ch.heigvd.dai.model.mail.Person;
import java.util.List;

/**
 * This Interface is an interface for the class ConfigruationManager
 * @author Anthony David
 * @author Stéphane Nascimento Santos
 */
public interface IConfigurationManager {
    List<Person> getVictims();

    List<String> getMessages();

    int getNumberOfGroups();

    String getSmtpServerAddr();

    int getSmtpServerPort();
}
