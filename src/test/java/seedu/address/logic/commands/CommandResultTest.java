package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");


        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));


        assertTrue(commandResult.equals(commandResult));


        assertFalse(commandResult.equals(null));


        assertFalse(commandResult.equals(0.5f));


        assertFalse(commandResult.equals(new CommandResult("different")));


        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));


        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");


        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());


        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());


        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());


        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + "}";
        assertEquals(expected, commandResult.toString());
    }
}
