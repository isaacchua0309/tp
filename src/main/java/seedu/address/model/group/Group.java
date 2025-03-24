// Represents a named group of UniquePersonList
package seedu.address.model.group;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a named group that contains a list of unique persons.
 * Each group has a unique group name and a {@link UniquePersonList} as members.
 */
public class Group {
    private final String groupName;
    private final UniquePersonList members;

    /**
     * Constructs a {@code Group} with the given name and an empty list of members.
     *
     * @param groupName The name of the group.
     */
    public Group(String groupName) {
        this.groupName = groupName;
        this.members = new UniquePersonList();
    }

    public String getGroupName() {
        return groupName;
    }

    public UniquePersonList getMembers() {
        return members;
    }

    public void addMember(Person person) {
        members.add(person);
    }

    public void removeMember(Person person) {
        members.remove(person);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Group
                && ((Group) other).groupName.equals(this.groupName);
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }

    @Override
    public String toString() {
        return "Group: " + groupName + ", Members: " + members.asUnmodifiableObservableList().size();
    }
}
