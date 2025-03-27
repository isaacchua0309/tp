// Represents a named group of UniquePersonList
package seedu.address.model.group;

import java.util.List;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a named group that contains a list of unique persons.
 * Each group has a unique group name and a {@link UniquePersonList} as members.
 */
public class Group {
    private final Name groupName;
    private final UniquePersonList members;

    /**
     * Constructs a {@code Group} with the given name and an empty list of members.
     *
     * @param groupName The name of the group.
     */
    public Group(Name groupName) {
        this.groupName = groupName;
        this.members = new UniquePersonList();
    }

    /**
     * Constructs a {@code Group} with the given name and an empty list of members.
     * @param groupName The name of the group.
     * @param members List of members
     */
    public Group(Name groupName, List<Person> members) {
        this.groupName = groupName;
        UniquePersonList toAddMembers = new UniquePersonList();
        toAddMembers.setPersons(members);
        this.members = toAddMembers;
    }

    public Name getGroupName() {
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

    /**
     * Returns true if both groups have the same name.
     * Defines weaker notion of equality between two groups to ensure that no two groups of same name exist
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
                && otherGroup.getGroupName().equals(getGroupName());
    }

    /**
     * Returns true if both groups have the same name and data fields
     * Defines stronger notion of equality between two groups
     */
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
