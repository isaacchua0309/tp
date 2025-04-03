package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.game.Game;
/**
 * An UI component that displays information of a {@code Game}.
 * The displayed index corresponds to the game's position in the date/time sorted list.
 */
public class GameCard extends UiPart<Region> {
    private static final String FXML = "GameListCard.fxml";

    public final Game game;

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label gameName;

    @FXML
    private FlowPane participants;

    @FXML
    private Label dateTime;

    @FXML
    private Label locationLabel;


    /**
     * Creates a {@code GameCard} with the given {@code Game} and index to display.
     * The displayedIndex corresponds to the game's position in the date/time sorted list,
     * ensuring consistency between UI display and command references.
     */
    public GameCard(Game game, int displayedIndex) {
        super(FXML);
        requireNonNull(game);
        this.game = game;
        id.setText(displayedIndex + ". ");
        gameName.setText(game.getSport().toString());


        game.getParticipants().forEach(p -> {
            Label participantLabel = new Label(p.getName().fullName);

            participantLabel.getStyleClass().add("label");

            participantLabel.setText(p.getName().fullName + " ");
            participants.getChildren().add(participantLabel);
        });

        dateTime.setText("Date/Time: " + game.getDateTime());
        locationLabel.setText("Location: " + game.getLocation().getAddress().toString());
    }
}
