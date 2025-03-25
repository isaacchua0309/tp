// Just like UniquePersonList, to hold all groups with name uniqueness
package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateGroupException;

/**
 * A list of groups that enforces uniqueness between its elements and does not allow nulls.
 * A group is considered unique based on its group name.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueGroupList implements Iterable<Group> {
    private final ObservableList<Group> internalList = FXCollections.observableArrayList();
    private final ObservableList<Group> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a group to the list.
     * The group must not already exist in the list.
     *
     * @param group The group to be added.
     * @throws DuplicateGroupException if the group already exists.
     */
    public void add(Group group) {
        if (contains(group.getGroupName())) {
            throw new DuplicateGroupException();
        }
        internalList.add(group);
    }

    /**
     * Returns true if a group with the given name exists in the list.
     *
     * @param groupName The name of the group to check.
     * @return true if the group exists, false otherwise.
     */
    public boolean contains(String groupName) {
        return internalList.stream().anyMatch(g -> g.getGroupName().equals(groupName));
    }

    public Group get(String groupName) {
        return internalList.stream().filter(g -> g.getGroupName().equals(groupName)).findFirst().orElse(null);
    }

    public ObservableList<Group> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public void setGroups(List<Group> groups) {
        requireAllNonNull(groups);
        if (!groupsAreUnique(groups)) {
            throw new DuplicateGroupException();
        }
        internalList.setAll(groups);
    }

    /**
     * Replaces the contents of this list with {@code groups}.
     * {@code groups} must not contain duplicate group names.
     */
    public void setGroups(UniqueGroupList replacement) {
        requireNonNull(replacement);
        setGroups(replacement.internalList);
    }

    /**
     * Returns true if {@code groups} contains only unique groups
     * (i.e., no two groups have the same name).
     */
    private boolean groupsAreUnique(List<Group> groups) {
        for (int i = 0; i < groups.size() - 1; i++) {
            for (int j = i + 1; j < groups.size(); j++) {
                if (groups.get(i).getGroupName().equals(groups.get(j).getGroupName())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UniqueGroupList)) {
            return false;
        }
        UniqueGroupList otherList = (UniqueGroupList) other;
        return internalList.equals(otherList.internalList);
    }

}
