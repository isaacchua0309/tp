package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPORT_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPORT_FOOTBALL;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sport;
import seedu.address.testutil.PersonBuilder;

public class AddSportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullSport_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSportCommand(INDEX_FIRST_PERSON.getZeroBased(), null));
    }

    @Test
    public void execute_addSportToPersonUnfilteredList_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithSport = new PersonBuilder(firstPerson).withSports(VALID_SPORT_FOOTBALL).build();
        
        AddSportCommand addSportCommand = new AddSportCommand(
                INDEX_FIRST_PERSON.getZeroBased(), new Sport(VALID_SPORT_FOOTBALL));
        
        String expectedMessage = String.format(
                AddSportCommand.MESSAGE_SUCCESS, VALID_SPORT_FOOTBALL, firstPerson.getName().fullName);
        
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, personWithSport);
        
        CommandResult result = addSportCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }
    
    @Test
    public void execute_addSportToPersonWithExistingSport_throwsCommandException() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // First add a sport
        AddSportCommand addSportCommand = new AddSportCommand(
                INDEX_FIRST_PERSON.getZeroBased(), new Sport(VALID_SPORT_FOOTBALL));
        addSportCommand.execute(model);
        
        // Try adding the same sport again
        assertThrows(CommandException.class, 
                AddSportCommand.MESSAGE_DUPLICATE_SPORT, () -> addSportCommand.execute(model));
    }
    
    @Test
    public void execute_invalidPersonIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddSportCommand addSportCommand = new AddSportCommand(
                outOfBoundIndex.getZeroBased(), new Sport(VALID_SPORT_FOOTBALL));
        
        assertThrows(CommandException.class, 
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> addSportCommand.execute(model));
    }
    
    @Test
    public void equals() {
        Sport football = new Sport(VALID_SPORT_FOOTBALL);
        Sport basketball = new Sport(VALID_SPORT_BASKETBALL);
        AddSportCommand addFootballCommand = new AddSportCommand(INDEX_FIRST_PERSON.getZeroBased(), football);
        AddSportCommand addBasketballCommand = new AddSportCommand(INDEX_FIRST_PERSON.getZeroBased(), basketball);
        AddSportCommand addFootballSecondCommand = new AddSportCommand(INDEX_SECOND_PERSON.getZeroBased(), football);
        
        // same object -> returns true
        assertTrue(addFootballCommand.equals(addFootballCommand));
        
        // same values -> returns true
        AddSportCommand addFootballCommandCopy = new AddSportCommand(INDEX_FIRST_PERSON.getZeroBased(), football);
        assertTrue(addFootballCommand.equals(addFootballCommandCopy));
        
        // different types -> returns false
        assertFalse(addFootballCommand.equals(1));
        
        // null -> returns false
        assertFalse(addFootballCommand.equals(null));
        
        // different sport -> returns false
        assertFalse(addFootballCommand.equals(addBasketballCommand));
        
        // different index -> returns false
        assertFalse(addFootballCommand.equals(addFootballSecondCommand));
    }
    
    @Test
    public void toStringMethod() {
        Sport football = new Sport(VALID_SPORT_FOOTBALL);
        AddSportCommand command = new AddSportCommand(INDEX_FIRST_PERSON.getZeroBased(), football);
        String expected = AddSportCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_PERSON.getZeroBased()
                + ", sport=" + football + "}";
        assertEquals(expected, command.toString());
    }
} 