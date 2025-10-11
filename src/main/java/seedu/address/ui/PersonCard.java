package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane categories;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getCategories().stream()
                .sorted(Comparator.comparing(category -> category.value))
                .forEach(category -> {
                    Label catLabel = new Label(category.value);
                    String bgColor = boxColor(category.category);

                    catLabel.getStyleClass().add("category-box");
                    catLabel.setStyle("-fx-background-color: "
                            + (bgColor.isEmpty() ? "#A9A9A9" : bgColor) + ";");
                    categories.getChildren().add(catLabel);
                });
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private String boxColor(String category) {
        StringBuilder sb = new StringBuilder();
        switch (category) {
            case "Role":
                sb.append("#008B8B"); //DarkCyan
                break;
            case "Department":
                sb.append("#006400"); //DarkGreen
                break;
            case "Team":
                sb.append("#FF1493"); //DeepPink
                break;
            default:
                break;
        }

        return sb.toString();
    }
}
