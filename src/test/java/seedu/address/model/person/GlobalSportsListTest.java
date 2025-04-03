package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.UserPrefs;

/**
 * Contains tests for the global sports list functionality in {@code Sport} class.
 */
public class GlobalSportsListTest {

    @TempDir
    public Path testFolder;

    private Path testSportsFile;

    @BeforeEach
    public void setUp() throws IOException {
        // Create test sports file in temporary directory
        testSportsFile = testFolder.resolve("testGlobalSportList.json");

        // Reset to default sports before each tes
        Sport.loadDefaultSports();
    }

    @AfterEach
    public void tearDown() {
        // Reset to default sports
        Sport.loadDefaultSports();
    }

    @Test
    public void getValidSports_afterLoadDefaultSports_returnsDefaultSports() {
        Set<String> validSports = Sport.getValidSports();

        // Default sports should include these standard sports
        assertTrue(validSports.contains("soccer"));
        assertTrue(validSports.contains("basketball"));
        assertTrue(validSports.contains("tennis"));
        assertTrue(validSports.contains("badminton"));
        assertTrue(validSports.contains("cricket"));
        assertTrue(validSports.contains("baseball"));
        assertTrue(validSports.contains("volleyball"));
        assertTrue(validSports.contains("hockey"));
        assertTrue(validSports.contains("rugby"));
        assertTrue(validSports.contains("golf"));

        // Should have exactly 10 default sports
        assertEquals(10, validSports.size());
    }

    @Test
    public void loadValidSports_newFile_createsWithDefaultSports() throws IOException {
        // Ensure file doesn't exist ye
        assertFalse(Files.exists(testSportsFile));

        // Load sports from non-existent file (should create with defaults)
        Sport.loadValidSports(testSportsFile);

        // Verify file was created
        assertTrue(Files.exists(testSportsFile));

        // Verify default sports were loaded
        Set<String> validSports = Sport.getValidSports();
        assertEquals(10, validSports.size());
        assertTrue(validSports.contains("soccer"));
        assertTrue(validSports.contains("basketball"));
    }

    @Test
    public void loadValidSports_existingFile_loadsFromFile() throws IOException {
        // Create a file with only two sports
        Set<String> originalSports = Set.of("cycling", "swimming");
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        mapper.writeValue(testSportsFile.toFile(), originalSports);

        // Load from the file
        Sport.loadValidSports(testSportsFile);

        // Verify correct sports were loaded
        Set<String> validSports = Sport.getValidSports();
        assertEquals(2, validSports.size());
        assertTrue(validSports.contains("cycling"));
        assertTrue(validSports.contains("swimming"));
        assertFalse(validSports.contains("soccer")); // Default sport should not be presen
    }

    @Test
    public void saveValidSports_newSport_savesCorrectly() throws IOException {
        // Start with default sports
        Sport.loadDefaultSports();

        // Add a new spor
        Sport.createValidSport("archery");

        // Save to file
        Sport.saveValidSports(testSportsFile);

        // Reset sports
        Sport.loadDefaultSports();

        // Verify new sport is not present after rese
        assertFalse(Sport.isValidSport("archery"));

        // Reload from file
        Sport.loadValidSports(testSportsFile);

        // Verify new sport is now presen
        assertTrue(Sport.isValidSport("archery"));
    }

    @Test
    public void loadSaveValidSports_withUserPrefs_worksCorrectly() throws IOException {
        // Create UserPrefs with test file path
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGlobalSportsListFilePath(testSportsFile);

        // Load sports from UserPrefs path
        Sport.loadValidSports(userPrefs.getGlobalSportsListFilePath());

        // Add a new spor
        Sport.createValidSport("archery");

        // Save sports to UserPrefs path
        Sport.saveValidSports(userPrefs.getGlobalSportsListFilePath());

        // Reset sports
        Sport.loadDefaultSports();

        // Verify sport was removed
        assertFalse(Sport.isValidSport("archery"));

        // Reload from UserPrefs path
        Sport.loadValidSports(userPrefs.getGlobalSportsListFilePath());

        // Verify sport was loaded
        assertTrue(Sport.isValidSport("archery"));
    }

    @Test
    public void createValidSport_newSport_returnsTrue() {
        // Add a new spor
        boolean result = Sport.createValidSport("archery");

        // Should return true for a new spor
        assertTrue(result);

        // Sport should now be valid
        assertTrue(Sport.isValidSport("archery"));
    }

    @Test
    public void createValidSport_existingSport_returnsFalse() {
        // Add an existing spor
        boolean result = Sport.createValidSport("soccer"); // "soccer" is in default sports

        // Should return false for an existing spor
        assertFalse(result);
    }

    @Test
    public void createValidSport_existingSportDifferentCase_returnsFalse() {
        // Add an existing sport with different case
        boolean result = Sport.createValidSport("SoCcEr"); // "soccer" is in default sports

        // Should return false for an existing sport (case-insensitive)
        assertFalse(result);
    }

    @Test
    public void isValidSport_validAndInvalidSports_returnsCorrectResults() {
        // Valid sports (in default list)
        assertTrue(Sport.isValidSport("soccer"));
        assertTrue(Sport.isValidSport("basketball"));

        // Valid sports with different case
        assertTrue(Sport.isValidSport("SOCCER"));
        assertTrue(Sport.isValidSport("Basketball"));

        // Valid sports with whitespace
        assertTrue(Sport.isValidSport(" soccer "));

        // Invalid sports (not in default list)
        assertFalse(Sport.isValidSport("archery"));
        assertFalse(Sport.isValidSport("swimming"));

        // Null argumen
        assertThrows(NullPointerException.class, () -> Sport.isValidSport(null));
    }
}
