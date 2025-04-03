package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddSportCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CreateSportCommand;
import seedu.address.logic.commands.DeleteSportCommand;
import seedu.address.logic.commands.FindSportCommand;
import seedu.address.logic.commands.ListSportsCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sport;
import seedu.address.model.person.SportContainsKeywordsPredicate;
import seedu.address.model.person.SportList;

/**
 * Contains integration tests for the global sports list functionality.
 */
public class GlobalSportsIntegrationTest {

    @TempDir
    public Path testFolder;

    private Path testSportsFile;
    private Model model;
    private UserPrefs userPrefs;

    @BeforeEach
    public void setUp() throws IOException {
        // Create test sports file in temporary directory
        testSportsFile = testFolder.resolve("testIntegrationSportList.json");

        // Create custom UserPrefs with test file path
        userPrefs = new UserPrefs();
        userPrefs.setGlobalSportsListFilePath(testSportsFile);

        // Reset to default sports before each tes
        Sport.loadDefaultSports();

        // Initialize model with typical address book
        model = new ModelManager(getTypicalAddressBook(), userPrefs);
    }

    @AfterEach
    public void tearDown() {
        // Reset to default sports
        Sport.loadDefaultSports();
    }

    @Test
    public void createSportCommand_addSportCommand_integration() throws CommandException {
        // Create a new sport not in the default lis
        String newSportName = "archery";
        CreateSportCommand createSportCommand = new CreateSportCommand(newSportName, testSportsFile);

        try {
            // Execute create sport command
            CommandResult createResult = createSportCommand.execute(model);
            assertEquals(String.format(CreateSportCommand.MESSAGE_SUCCESS, newSportName),
                    createResult.getFeedbackToUser());

            // Verify the sport was added to valid sports
            assertTrue(Sport.isValidSport(newSportName));

            // Get first person from the model
            Person firstPerson = model.getFilteredPersonList().get(0);

            // Create command to add the new sport to the person
            AddSportCommand addSportCommand = new AddSportCommand(0, new Sport(newSportName));

            // Execute add sport command
            CommandResult addResult = addSportCommand.execute(model);
            assertEquals(String.format(AddSportCommand.MESSAGE_SUCCESS,
                    newSportName, firstPerson.getName().fullName), addResult.getFeedbackToUser());

            // Verify the person now has the spor
            Person updatedPerson = model.getFilteredPersonList().get(0);
            boolean hasSport = updatedPerson.getSports().contains(new Sport(newSportName));
            assertTrue(hasSport);
        } finally {
            // Restore original valid sports
            Sport.loadDefaultSports();
        }
    }

    @Test
    public void findSportCommand_integration() throws CommandException {
        // First, create a unique sport for this tes
        String uniqueSportName = "uniquesport";
        CreateSportCommand createSportCommand = new CreateSportCommand(uniqueSportName, testSportsFile);
        createSportCommand.execute(model);

        // Store reference to first person before modification
        Person originalPerson = model.getFilteredPersonList().get(0);

        // Add the unique sport to the first person
        AddSportCommand addSportCommand = new AddSportCommand(0, new Sport(uniqueSportName));
        addSportCommand.execute(model);

        // Create the find sport command with the unique spor
        List<String> sportKeywords = List.of(uniqueSportName);
        FindSportCommand findSportCommand = new FindSportCommand(
                new SportContainsKeywordsPredicate(sportKeywords), sportKeywords);

        // Execute the find command
        CommandResult findResult = findSportCommand.execute(model);

        // Verify that exactly one person was found
        assertEquals(1, model.getFilteredPersonList().size());

        // Verify that the found person has the unique spor
        Person foundPerson = model.getFilteredPersonList().get(0);
        assertTrue(foundPerson.getSports().contains(new Sport(uniqueSportName)));

        // Verify that the found person has the same name, phone, email, etc. as the original person
        assertEquals(originalPerson.getName(), foundPerson.getName());
        assertEquals(originalPerson.getPhone(), foundPerson.getPhone());
        assertEquals(originalPerson.getEmail(), foundPerson.getEmail());
        assertEquals(originalPerson.getAddress(), foundPerson.getAddress());
    }

    @Test
    public void deleteValidSport_integration() throws CommandException {
        // Get the sorted list of sports
        List<String> sortedSports = Sport.getSortedValidSports();
        int originalSize = sortedSports.size();
        String sportToDelete = sortedSports.get(0); // First sport in alphabetical order

        // Create command to delete the sport using index 0 (first sport)
        DeleteSportCommand command = new DeleteSportCommand(Index.fromZeroBased(0), testSportsFile);

        // Execute the command
        CommandResult result = command.execute(model);

        // Verify the result message
        assertEquals(String.format(DeleteSportCommand.MESSAGE_DELETE_SPORT_SUCCESS, sportToDelete),
                result.getFeedbackToUser());

        // Verify the sport was deleted from the valid sports
        assertFalse(Sport.isValidSport(sportToDelete));

        // Verify the size of the valid sports decreased
        assertEquals(originalSize - 1, Sport.getValidSports().size());

        // Verify the sport was actually removed from the lis
        List<String> updatedSports = Sport.getSortedValidSports();
        assertFalse(updatedSports.contains(sportToDelete));
    }

    @Test
    public void deleteSportCommand_invalidIndex_throwsCommandException() {
        // Get the size of the valid sports lis
        int size = Sport.getSortedValidSports().size();

        // Create command with an invalid index (equal to size)
        DeleteSportCommand command = new DeleteSportCommand(Index.fromZeroBased(size));

        // Should throw exception since the index is out of bounds
        assertThrows(CommandException.class, DeleteSportCommand.MESSAGE_INVALID_SPORT_INDEX, () ->
                command.execute(model));
    }

    @Test
    public void listSportsCommand_integration() throws CommandException {
        // Get the sorted list of sports
        List<String> sortedSports = Sport.getSortedValidSports();

        // Create and execute the list sports command
        ListSportsCommand command = new ListSportsCommand();
        CommandResult result = command.execute(model);

        // Verify that the result message contains the expected header
        assertTrue(result.getFeedbackToUser().startsWith(ListSportsCommand.MESSAGE_SUCCESS));

        // Verify that the result message contains each sport with its index
        for (int i = 0; i < sortedSports.size(); i++) {
            String expectedLine = String.format("%d. %s", i + 1, sortedSports.get(i));
            assertTrue(result.getFeedbackToUser().contains(expectedLine),
                    "Result should contain: " + expectedLine);
        }
    }

    @Test
    public void sportList_manipulation_integration() {
        // Get first person from model
        Person firstPerson = model.getFilteredPersonList().get(0);

        // Get their sport lis
        SportList sportList = firstPerson.getSportList();
        int originalSize = sportList.size();

        // Add a new sport to the lis
        Sport newSport = new Sport("tennis");
        sportList.add(newSport);

        // Verify the sport list now contains the new spor
        assertEquals(originalSize + 1, sportList.size());
        assertTrue(sportList.contains(newSport));

        // Create a new person with the updated sport lis
        Person updatedPerson = new Person(
                firstPerson.getName(),
                firstPerson.getPhone(),
                firstPerson.getEmail(),
                firstPerson.getAddress(),
                firstPerson.getPostalCode(),
                firstPerson.getTags(),
                sportList);

        // Update the model
        model.setPerson(firstPerson, updatedPerson);

        // Verify the updated person has the new spor
        Person modelPerson = model.getFilteredPersonList().get(0);
        assertTrue(modelPerson.getSports().contains(newSport));
    }
}
