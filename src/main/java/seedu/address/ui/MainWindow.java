package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String LIGHT_THEME_PATH = "/view/LightTheme.css";
    private static final String DARK_THEME_PATH = "/view/DarkTheme.css";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private boolean isDarkTheme = true;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private GameListPanel gameListPanel;
    private ResultDisplay resultDisplay;
    private SportsListPanel sportsListPanel;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private Button themeToggleButton;

    @FXML
    private SplitPane centerSplitpane;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane gameListPanelPlaceholder;

    @FXML
    private StackPane sportsListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        setAccelerators();

        // Apply dark theme styling to scene and roo
        applyDarkThemeStyling();

        helpWindow = new HelpWindow();
    }

    /**
     * Applies dark theme styling to the scene and root node
     */
    private void applyDarkThemeStyling() {
        Scene scene = primaryStage.getScene();
        if (scene != null) {
            // Set the scene background fill color
            scene.setFill(javafx.scene.paint.Color.valueOf("#1e2030"));

            // Apply background style to root container
            VBox root = (VBox) scene.getRoot();
            if (root != null) {
                root.setStyle("-fx-background-color: #1e2030;");
            }
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator.
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        // See the issue description in the original code for details.
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        // Updated: use GameListPanel and getFilteredGameList() instead of group-related methods.
        gameListPanel = new GameListPanel(logic.getFilteredGameList());
        gameListPanelPlaceholder.getChildren().add(gameListPanel.getRoot());

        // Add sports list panel
        sportsListPanel = new SportsListPanel();
        sportsListPanelPlaceholder.getChildren().add(sportsListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Toggles between light and dark themes.
     */
    @FXML
    private void handleToggleTheme() {
        Scene scene = primaryStage.getScene();
        isDarkTheme = !isDarkTheme;

        if (isDarkTheme) {
            // Switch to dark theme
            scene.getStylesheets().remove(MainWindow.class.getResource(LIGHT_THEME_PATH).toExternalForm());
            scene.getStylesheets().add(0, MainWindow.class.getResource(DARK_THEME_PATH).toExternalForm());
            scene.setFill(javafx.scene.paint.Color.valueOf("#1e2030")); // Set dark background

            // Apply dark style to root container
            VBox root = (VBox) scene.getRoot();
            if (root != null) {
                root.setStyle("-fx-background-color: #1e2030;");
            }

            themeToggleButton.setText("💡 Switch to Light");
            logger.info("Switched to dark theme");
        } else {
            // Switch to light theme
            scene.getStylesheets().remove(MainWindow.class.getResource(DARK_THEME_PATH).toExternalForm());
            scene.getStylesheets().add(0, MainWindow.class.getResource(LIGHT_THEME_PATH).toExternalForm());
            scene.setFill(javafx.scene.paint.Color.valueOf("#f5f5f7")); // Set light background

            // Apply light style to root container
            VBox root = (VBox) scene.getRoot();
            if (root != null) {
                root.setStyle("-fx-background-color: #f5f5f7;");
            }

            themeToggleButton.setText("🌙 Switch to Dark");
            logger.info("Switched to light theme");
        }

        // Update the help window if it's already open.
        if (helpWindow.isShowing()) {
            helpWindow.applyTheme(isDarkTheme);
            Platform.runLater(() -> {
                helpWindow.getRoot().toFront();
                helpWindow.getRoot().requestFocus();
                logger.info("Help window theme updated and focused");
            });
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
            helpWindow.applyTheme(isDarkTheme);
            Platform.runLater(() -> {
                helpWindow.getRoot().toFront();
                helpWindow.getRoot().requestFocus();
                logger.info("Help window displayed and focused");
            });
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            // Update the sports list panel after command execution to reflect any changes
            sportsListPanel.updateSportsList();

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
