package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_PYTHON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Category;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;

public class UpdateCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

    private UpdateCommandParser parser = new UpdateCommandParser();

    // --- Failure scenarios ---

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", UpdateCommand.MESSAGE_NOT_UPDATED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        assertParseFailure(parser, "1 " + PREFIX_NAME + "John " + PREFIX_NAME + "Jane",
                String.format(Messages.MESSAGE_DUPLICATE_FIELDS + PREFIX_NAME));
        assertParseFailure(parser, "1 " + PREFIX_PHONE + "91234567 " + PREFIX_PHONE + "98765432",
                String.format(Messages.MESSAGE_DUPLICATE_FIELDS + PREFIX_PHONE));
    }

    // --- Success scenarios ---

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY + NAME_DESC_AMY
                + VALID_DEPARTMENT + VALID_TEAM + VALID_ROLE + VALID_SKILL_PYTHON + VALID_SKILL_JAVA;

        Set<Category> categoriesSet = new HashSet<>();
        categoriesSet.add(new Category("Department", "Finance"));
        categoriesSet.add(new Category("Team", "Team A"));
        categoriesSet.add(new Category("Role", "HR"));
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY)
                .withCategories(categoriesSet)
                .withSkills("Python", "Java")
                .build();

        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY)
                .build();

        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_PERSON;

        // Name only
        String userInputName = targetIndex.getOneBased() + NAME_DESC_AMY;
        UpdatePersonDescriptor descName = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        UpdateCommand expectedNameCommand = new UpdateCommand(targetIndex, descName);
        assertParseSuccess(parser, userInputName, expectedNameCommand);

        // Phone only
        String userInputPhone = targetIndex.getOneBased() + PHONE_DESC_AMY;
        UpdatePersonDescriptor descPhone = new UpdatePersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        UpdateCommand expectedPhoneCommand = new UpdateCommand(targetIndex, descPhone);
        assertParseSuccess(parser, userInputPhone, expectedPhoneCommand);

        // Email only
        String userInputEmail = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        UpdatePersonDescriptor descEmail = new UpdatePersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        UpdateCommand expectedEmailCommand = new UpdateCommand(targetIndex, descEmail);
        assertParseSuccess(parser, userInputEmail, expectedEmailCommand);
    }


    @Test
    public void parse_resetSkills_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_SKILL + "";

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withSkills().build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // --- Category tests ---

    @Test
    public void parse_singleCategory_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DEPARTMENT + "Finance";

        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Department", "Finance"));

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptor();
        descriptor.setCategories(categories);

        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleCategories_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased()
                + " " + PREFIX_DEPARTMENT + "IT"
                + " " + PREFIX_TEAM + "Alpha"
                + " " + PREFIX_ROLE + "Developer";

        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Department", "IT"));
        categories.add(new Category("Team", "Alpha"));
        categories.add(new Category("Role", "Developer"));

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptor();
        descriptor.setCategories(categories);

        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // --- Skill tests ---

    @Test
    public void parse_singleSkill_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_SKILL + "Java";

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withSkills("Java")
                .build();

        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleSkills_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased()
                + " " + PREFIX_SKILL + "Java"
                + " " + PREFIX_SKILL + "Python";

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withSkills("Java", "Python")
                .build();

        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // --- Edge cases ---

    @Test
    public void parse_extraTextAfterIndex_failure() {
        String userInput = "1 extratext" + NAME_DESC_AMY;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_largeIndex_success() {
        Index targetIndex = Index.fromOneBased(999999);
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

