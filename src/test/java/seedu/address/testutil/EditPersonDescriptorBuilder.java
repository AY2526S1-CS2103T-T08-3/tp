package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Skill;
import seedu.address.model.tag.Category;

/**
 * A utility class to help with building {@code EditPersonDescriptor} objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details.
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setCategories(person.getCategories());
        descriptor.setSkills(person.getSkills());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param name a valid name string.
     * @return this builder for chaining.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param phone a valid phone string.
     * @return this builder for chaining.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param email a valid email string.
     * @return this builder for chaining.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withCategories(List<Category> categoryArray) {
        Set<Category> categorySet = new HashSet<>(categoryArray);
        descriptor.setCategories(categorySet);
        return this;
    }

    // ---------------- Back-compat TAG helpers ----------------

    /**
     * Sets the {@code Tag}s of the {@code EditPersonDescriptor} we are building.
     *
     * @param tags one or more tag names.
     * @return this builder for chaining.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Skill> tagSet = Stream.of(tags).map(Skill::new).collect(Collectors.toSet());
        descriptor.setSkills(tagSet);
        return this;
    }

    /**
     * Clears all {@code Tag}s of the {@code EditPersonDescriptor}.
     *
     * @return this builder for chaining.
     */
    public EditPersonDescriptorBuilder withTags() {
        descriptor.setTags(new HashSet<>());
        return this;
    }

    // ---------------- New SKILL helpers (bridge to tags) ----------------

    /**
     * Sets the {@code Skill}s of the {@code EditPersonDescriptor} we are building.
     * Internally mapped to tags for compatibility with the current model.
     *
     * @param skills one or more skill names.
     * @return this builder for chaining.
     */
    public EditPersonDescriptorBuilder withSkills(String... skills) {
        Set<Skill> skillSet = Arrays.stream(skills).map(Skill::new).collect(Collectors.toSet());
        // Use bridge method on descriptor to map skills -> tags internally
        descriptor.setSkills(skillSet);
        return this;
    }

    /**
     * Clears all {@code Skill}s of the {@code EditPersonDescriptor}.
     * Internally implemented by clearing tags.
     *
     * @return this builder for chaining.
     */
    public EditPersonDescriptorBuilder withSkills() {
        descriptor.setTags(new HashSet<>());
        return this;
    }

    /**
     * Builds and returns the {@code EditPersonDescriptor}.
     *
     * @return the built {@code EditPersonDescriptor}.
     */
    public EditPersonDescriptor build() {
        return descriptor;
    }
}

