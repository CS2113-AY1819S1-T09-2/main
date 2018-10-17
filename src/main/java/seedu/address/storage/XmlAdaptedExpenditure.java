package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expenditureinfo.Category;
import seedu.address.model.expenditureinfo.Date;
import seedu.address.model.expenditureinfo.Expenditure;
import seedu.address.model.expenditureinfo.Money;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedExpenditure {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expenditure's %s field is missing!";

    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String money;
    @XmlElement(required = true)
    private String category;


    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedExpenditure() {}

    /**
     * Constructs an {@code XmlAdaptedExpenditure} with the given person details.
     */
    public XmlAdaptedExpenditure(String date, String money, String category) {
        this.date = date;
        this.money = money;
        this.category = category;
    }

    /**
     * Converts a given Expenditure into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedExpenditure(Expenditure source) {
        date = source.getDate().addingDate;
        money = source.getMoney().addingMoney;
        category = source.getCategory().categoryName;
    }

    /**
     * Converts this jaxb-friendly adapted expenditure object into the model's expenditure object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted expenditure
     */
    public Expenditure toModelType() throws IllegalValueException {

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (money == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Money.class.getSimpleName()));
        }
        if (!Money.isValidMoney(money)) {
            throw new IllegalValueException(Money.MESSAGE_MONEY_CONSTRAINTS);
        }
        final Money modelMoney = new Money(money);

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Category.class.getSimpleName()));
        }
        if (!Category.isValidCategory(category)) {
            throw new IllegalValueException(Category.MESSAGE_CATEGORY_CONSTRAINTS);
        }
        final Category modelCategory = new Category(category);

        return new Expenditure(modelDate, modelMoney, modelCategory);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedExpenditure otherExpenditure = (XmlAdaptedExpenditure) other;
        return Objects.equals(date, otherExpenditure.date)
                && Objects.equals(money, otherExpenditure.money)
                && Objects.equals(category, otherExpenditure.category);
    }
}
