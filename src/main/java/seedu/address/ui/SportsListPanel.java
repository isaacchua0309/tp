package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Sport;

/**
 * Panel containing the list of valid sports.
 */
public class SportsListPanel extends UiPart<Region> {
    private static final String FXML = "SportsListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SportsListPanel.class);

    @FXML
    private ListView<String> sportsListView;

    @FXML
    private Label titleLabel;

    /**
     * Creates a {@code SportsListPanel} with the given list of valid sports.
     */
    public SportsListPanel() {
        super(FXML);
        updateSportsList();
    }

    /**
     * Updates the sports list with the current valid sports.
     * This method should be called whenever the valid sports list changes.
     */
    public void updateSportsList() {
        List<String> sortedSports = Sport.getSortedValidSports();
        ObservableList<String> observableSportsList = FXCollections.observableArrayList(sortedSports);
        sportsListView.setItems(observableSportsList);
        sportsListView.setCellFactory(listView -> new SportListViewCell());
        logger.fine("Updated sports list with " + sortedSports.size() + " sports");
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a sport.
     */
    class SportListViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String sport, boolean empty) {
            super.updateItem(sport, empty);

            if (empty || sport == null) {
                setGraphic(null);
                setText(null);
            } else {
                HBox sportBox = new HBox(8);
                sportBox.getStyleClass().add("sport-item");

                Label indexLabel = new Label(Integer.toString(getIndex() + 1) + ".");
                indexLabel.getStyleClass().add("sport-index");

                Label nameLabel = new Label(sport);
                nameLabel.getStyleClass().add("sport-name");

                sportBox.getChildren().addAll(indexLabel, nameLabel);
                setGraphic(sportBox);
                setText(null);
            }
        }
    }
}
