package ch.heigvd.dai.model.prank;

import ch.heigvd.dai.config.ConfigurationManager;
import ch.heigvd.dai.config.IConfigurationManager;
import ch.heigvd.dai.model.mail.Group;
import ch.heigvd.dai.model.mail.Person;

import java.util.Collections;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Anthony David
 * @author Stéphane Nascimento Santos
 */
public class PrankGenerator {
    private final IConfigurationManager configurationManager;
    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());


    public PrankGenerator(IConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    public List<Prank> generatePranks() {
        List<Prank> pranks = new ArrayList<>();
        List<String> messages = configurationManager.getMessages();
        int messageIndex = 0;
        int numberOfGroups = configurationManager.getNumberOfGroups();
        int numberOfVictims = configurationManager.getVictims().size();

        if (numberOfVictims / numberOfGroups < 3) {
            numberOfGroups = numberOfVictims / 3;
            LOG.warning("There are not enough victims to generate the desired number of groups, We can only genereate a max of " + numberOfGroups + " groups to have at least 3 victims per groups.");
        }

        List<Group> groups = generateGroups(configurationManager.getVictims(), numberOfGroups);
        for (Group group : groups) {
            List<Person> victims = group.getMembers();
            Collections.shuffle(victims);
            Person sender = victims.remove(0);

            Prank prank  = new Prank();
            prank.setSender(sender);
            prank.addRecipiens(victims);

            String message = messages.get(messageIndex);
            messageIndex = (messageIndex + 1) % messages.size();
            prank.setMessage(message);

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
            Person victim = availableVictims.remove(0);
            targetGroup.addMember(victim);
        }
        return groups;
    }
}
