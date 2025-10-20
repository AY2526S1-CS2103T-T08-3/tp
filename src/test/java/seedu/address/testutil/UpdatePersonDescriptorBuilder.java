package seedu.address.testutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Skill;
import seedu.address.model.tag.Category;

/**
 * A utility class to help with building UpdatePersonDescriptor objects.
 */
public class UpdatePersonDescriptorBuilder {

    private UpdatePersonDescriptor descriptor;

    public UpdatePersonDescriptorBuilder() {
        descriptor = new UpdatePersonDescriptor();
    }

    public UpdatePersonDescriptorBuilder(UpdatePersonDescriptor descriptor) {
        this.descriptor = new UpdatePersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code UpdatePersonDescriptor} with fields containing {@code person}'s details
     */
    public UpdatePersonDescriptorBuilder(Person person) {
        descriptor = new UpdatePersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setCategories(person.getCategories());
        descriptor.setSkills(person.getSkills());
    }

    /**
     * Sets the {@code Name} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code UpdatePersonDescriptor}
     * that we are building.
     */
    public UpdatePersonDescriptorBuilder withCategories(List<Category> categoryArray) {
        Set<Category> categorySet = new HashSet<>(categoryArray);
        descriptor.setCategories(categorySet);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code UpdatePersonDescriptor}
     * that we are building.
     */
    public UpdatePersonDescriptorBuilder withTags(String... tags) {
        Set<Skill> tagSet = Stream.of(tags).map(Skill::new).collect(Collectors.toSet());
        descriptor.setSkills(tagSet);
        return this;
    }

    public UpdatePersonDescriptor build() {
        return descriptor;
    }
}
