package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelloCommandTest {
    @Test
    public void execute_helloCommand_success() throws CommandException {
        Model model = new ModelManager();
        HelloCommand helloCommand = new HelloCommand();
        assertEquals(helloCommand.execute(model).getFeedbackToUser(), HelloCommand.MESSAGE_SUCCESS);
    }
    
    @Test
    public void execute_helloCommandWithName_success() throws CommandException {
        Model model = new ModelManager();
        String testName = "John Doe";
        HelloCommand helloCommand = new HelloCommand(testName);
        assertEquals(
            helloCommand.execute(model).getFeedbackToUser(), 
            String.format(HelloCommand.MESSAGE_SUCCESS_WITH_NAME, testName)
        );
    }
    
    @Test
    public void equals() {
        HelloCommand helloDefault1 = new HelloCommand();
        HelloCommand helloDefault2 = new HelloCommand();
        HelloCommand helloJohn = new HelloCommand("John");
        HelloCommand helloJane = new HelloCommand("Jane");
        
        // Same object -> returns true
        assertTrue(helloDefault1.equals(helloDefault1));
        
        // Same values -> returns true
        assertTrue(helloDefault1.equals(helloDefault2));
        
        // Different types -> returns false
        assertFalse(helloDefault1.equals(1));
        
        // Null -> returns false
        assertFalse(helloDefault1.equals(null));
        
        // Different name -> returns false
        assertFalse(helloJohn.equals(helloJane));
        
        // Default vs with name -> returns false
        assertFalse(helloDefault1.equals(helloJohn));
    }
}
