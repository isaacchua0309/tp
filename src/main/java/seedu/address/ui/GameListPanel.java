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
 */
public class GameListPanel extends UiPart<Region> {
    private static final String FXML = "GameListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GameListPanel.class);

    @FXML
    private ListView<Game> gameListView;

    /**
     * Creates a {@code GameListPanel} with the given {@code ObservableList}.
     */
    public GameListPanel(ObservableList<Game> gameList) {
        super(FXML);
        gameListView.setItems(gameList);
        gameListView.setCellFactory(listView -> new GameListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Game} using a {@code GameCard}.
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
