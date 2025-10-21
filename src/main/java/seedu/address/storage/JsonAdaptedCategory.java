package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Category;

/**
 * Jackson-friendly version of {@link Category}.
 */
class JsonAdaptedCategory {
    private final String category;
    private final String value;

    /**
     * Constructs a {@code JsonAdaptedCategory} with the given {@code category} and {@code value}.
     */
    @JsonCreator
    public JsonAdaptedCategory(@JsonProperty("category") String category,
                               @JsonProperty("value") String value) {
        this.category = category;
        this.value = value;
    }

    /**
     * Converts a given {@code Category} into this class for Jackson use.
     */
    public JsonAdaptedCategory(Category source) {
        this.category = source.getCategory();
        this.value = source.getValue();
    }

    public String getCategory() {
        return this.category;
    }

    public String getValue() {
        return this.value;
    }

    /**
     * Converts this Jackson-friendly adapted category object into the model's {@code Category} object.
     *
     * @throws NullPointerException if either category or value is null (as per test expectations).
     * @throws IllegalValueException if data constraints are violated.
     */
    public Category toModelType() throws IllegalValueException {
        // Tests expect a NullPointerException when category or value is null
        requireNonNull(category);
        requireNonNull(value);

        if (!Category.isValidData(category) || !Category.isValidData(value)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(category, value);
    }
}

