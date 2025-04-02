package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.address.model.game.Game;
/**
 * An UI component that displays information of a {@code Game}.
 */
public class GameCard extends UiPart<VBox> {
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
     */
    public GameCard(Game game, int displayedIndex) {
        super(FXML);
        requireNonNull(game);
        this.game = game;
        id.setText(displayedIndex + ". ");
        gameName.setText(game.getSport().toString());

        // Populate participants (if any)
        game.getParticipants().forEach(p -> {
            Label participantLabel = new Label(p.getName().fullName);
            participants.getChildren().add(participantLabel);
        });

        dateTime.setText("Date/Time: " + game.getDateTime());
        locationLabel.setText("Location: " + game.getLocation().getAddress().toString()); // or hide if not available
    }
}
