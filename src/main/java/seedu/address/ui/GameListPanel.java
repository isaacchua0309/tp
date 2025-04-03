package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.game.Game;

/**
 * Panel containing the list of games.
 * The games are displayed in date/time sorted order to ensure consistent indexing
 * between the UI display and command references.
 */
public class GameListPanel extends UiPart<Region> {
    private static final String FXML = "GameListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GameListPanel.class);

    @FXML
    private ListView<Game> gameListView;

    /**
     * Creates a {@code GameListPanel} with the given {@code ObservableList}.
     * The list is expected to be sorted by date/time from the Model.
     */
    public GameListPanel(ObservableList<Game> gameList) {
        super(FXML);
        gameListView.setItems(gameList);
        gameListView.setCellFactory(listView -> new GameListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Game} using a {@code GameCard}.
     * The index displayed is based on the game's position in the date/time sorted list.
     */
    class GameListViewCell extends ListCell<Game> {
        @Override
        protected void updateItem(Game game, boolean empty) {
            super.updateItem(game, empty);

            if (empty || game == null) {
                setGraphic(null);
                setText(null);
            } else {

                setGraphic(new GameCard(game, getIndex() + 1).getRoot());
            }
        }
    }
}
