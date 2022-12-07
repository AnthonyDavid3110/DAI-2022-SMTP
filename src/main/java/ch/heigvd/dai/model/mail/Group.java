package ch.heigvd.dai.model.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anthony David
 * @author Stéphane Nascimento Santos
 * @version 1.0
 */
public class Group {
  private final List<Person> members = new ArrayList<>();

    /**
     * Add a Person as member in the group
     * @param person to add in the group
     */
  public void addMember(Person person) {
      members.add(person);
  }

    /**
     * Getter of members
     * @return List<Person> of all the members of the group
     */
  public List<Person> getMembers() {
      return members;
  }
}
