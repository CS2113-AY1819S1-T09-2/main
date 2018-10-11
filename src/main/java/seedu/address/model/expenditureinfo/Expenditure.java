package seedu.address.model.expenditureinfo;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expenditure {

    // Data fields
    private final Date date;
    private final Money money;
    private final Category category;


    /**
     * Every field must be present and not null.
     */
    public Expenditure(Date date, Money money, Category category) {
        requireAllNonNull(date, money, category);
        this.date = date;
        this.money = money;
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public Money getMoney() {
        return money;
    }

    public Category getCategory() {
        return category;
    }



    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, money, category);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Date: ")
                .append(getDate())
                .append(" Money: ")
                .append(getMoney())
                .append(" Category ")
                .append(getCategory());
        return builder.toString();
    }

}
