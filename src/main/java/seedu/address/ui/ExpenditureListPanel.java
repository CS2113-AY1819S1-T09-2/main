package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExpenditurePanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.expenditureinfo.Expenditure;

/**
 * Panel containing the list of expenditures.
 */
public class ExpenditureListPanel extends UiPart<Region> {
    private static final String FXML = "ExpenditureListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExpenditureListPanel.class);

    @FXML
    private ListView<Expenditure> expenditureListView;

    public ExpenditureListPanel(ObservableList<Expenditure> expenditureList) {
        super(FXML);
        setConnections(expenditureList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Expenditure> expenditureList) {
        expenditureListView.setItems(expenditureList);
        expenditureListView.setCellFactory(listView -> new ExpenditureListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        expenditureListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in expenditure list panel changed to : '" + newValue + "'");
                        raise(new ExpenditurePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ExpenditureCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            expenditureListView.scrollTo(index);
            expenditureListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Expenditure} using a {@code ExpenditureCard}.
     */
    class ExpenditureListViewCell extends ListCell<Expenditure> {
        @Override
        protected void updateItem(Expenditure expenditure, boolean empty) {
            super.updateItem(expenditure, empty);

            if (empty || expenditure == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExpenditureCard(expenditure, getIndex() + 1).getRoot());
            }
        }
    }

}
