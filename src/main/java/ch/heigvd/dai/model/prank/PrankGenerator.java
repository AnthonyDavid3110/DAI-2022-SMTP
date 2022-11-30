package src.main.java.ch.heigvd.dai.model.prank;

import src.main.java.ch.heigvd.dai.config.ConfigurationManager;
import src.main.java.ch.heigvd.dai.config.IConfigurationManager;
import src.main.java.ch.heigvd.dai.model.mail.Group;
import src.main.java.ch.heigvd.dai.model.mail.Person;

import java.util.Collections;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anthony David
 * @author Stéphane Nascimento Santos
 */
public class PrankGenerator {
    private IConfigurationManager configurationManager;

    public PrankGenerator(IConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    public List<Prank> generatePrank() {
        List<Prank> pranks = new ArrayList<>();

        List<String> messages = configurationManager.getMessages();
        int messageIndex = 0;

        int numberOfGroups = configurationManager.getNumberOfGroups();
        List<Person> victimsList = configurationManager.getVictims();
        int numberOfVictims = victimsList.size();

        if (numberOfVictims / numberOfGroups < 3) {
            numberOfGroups = numberOfVictims / 3;
            throw new IllegalArgumentException("Thera are not enough victims to complete all the groups");
        }

        List<Group> groups = generateGroups(victimsList, numberOfGroups);
        for (Group group : groups) {
            Prank prank  = new Prank();

            List<Person> victims = group.getMembers();
            Collections.shuffle(victims);
            Person sender = victims.remove(0);
            prank.addRecipiens(victims);
            String message = messages.get(messageIndex);
            messageIndex = (messageIndex + 1) % messages.size();
            pranks.add(prank);
        }
    return pranks;
    }

    private List<Group> generateGroups(List<Person> victims, int numberOfGroups) {
        List<Person> availableVictims = new ArrayList<>(victims);
        Collections.shuffle(availableVictims);
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < numberOfGroups; i++){
            Group group = new Group();
            groups.add(group);
        }
        int turn = 0;
        Group targetGroup;
        while (availableVictims.size() > 0) {
            targetGroup = groups.get(turn);
            turn = (turn + 1) % groups.size();
            Person victim =availableVictims.remove(0);
            targetGroup.addMember(victim);
        }
        return groups;
    }
}
