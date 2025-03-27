// Just like UniquePersonList, to hold all groups with name uniqueness
package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;

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
        requireNonNull(group);
        if (contains(group)) {
            throw new DuplicateGroupException();
        }
        internalList.add(group);
    }

    /**
     * Removes the equivalent group from the list.
     * The group must exist in the list.
     */
    public void remove(Group toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Returns true if a group with the given name exists in the list.
     */
    public boolean contains(Group groupToCheck) {
        requireNonNull(groupToCheck);
        return internalList.stream().anyMatch(groupToCheck::isSameGroup);
    }

    public Group get(String groupName) {
        return internalList.stream().filter(g -> g.getGroupName().equals(groupName)).findFirst().orElse(null);
    }

    public ObservableList<Group> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Replaces the group {@code target} in the list with {@code editedGroup}.
     * {@code target} must exist in the list.
     * The group identity of {@code editedGroup} must not be the same as another existing group in the list.
     */
    public void setGroup(Group target, Group editedGroup) {
        requireAllNonNull(target, editedGroup);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GroupNotFoundException();
        }

        if (!target.isSameGroup(editedGroup) && contains(editedGroup)) {
            throw new DuplicateGroupException();
        }

        internalList.set(index, editedGroup);
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
                if (groups.get(i).isSameGroup(groups.get(j))) {
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
