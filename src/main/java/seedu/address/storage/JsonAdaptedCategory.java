package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
        this.category = source.category;
        this.value = source.value;
    }

    public String getCategory() {
        return this.category;
    }

    public String getValue() {
        return this.value;
    }

    /**
     * Converts this Jackson-friendly adapted category object into the model's {@code Category} object.
     */
    public Category toModelType() {
        return new Category(category, value);
    }

}
