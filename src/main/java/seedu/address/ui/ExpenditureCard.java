package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.expenditureinfo.Expenditure;

/**
 * An UI component that displays information of an {@code Expenditure}.
 */
public class ExpenditureCard extends UiPart<Region> {

    private static final String FXML = "ExpenditureListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Expenditure expenditure;

    @FXML
    private HBox expenditureCardPane;
    @FXML
    private Label category;
    @FXML
    private Label date;
    @FXML
    private Label id;
    @FXML
    private Label money;

    public ExpenditureCard(Expenditure expenditure, int displayedIndex) {
        super(FXML);
        this.expenditure = expenditure;
        id.setText(displayedIndex + ". ");
        category.setText(expenditure.getCategory().categoryName);
        date.setText(expenditure.getDate().addingDate);
        money.setText(expenditure.getMoney().addingMoney);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpenditureCard)) {
            return false;
        }

        // state check
        ExpenditureCard card = (ExpenditureCard) other;
        return id.getText().equals(card.id.getText())
                && expenditure.equals(card.expenditure);
    }
}
