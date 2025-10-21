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
        // Use skills (replacement for legacy tags).
        descriptor.setSkills(person.getSkills());
    }

    /** Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building. */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /** Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building. */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /** Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building. */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    // ---------------- SKILL helpers ----------------

    /** Sets the {@code Skill}s of the {@code EditPersonDescriptor} we are building. */
    public EditPersonDescriptorBuilder withSkills(String... skills) {
        Set<Skill> skillSet = Arrays.stream(skills)
                .map(Skill::new)
                .collect(Collectors.toSet());
        descriptor.setSkills(skillSet);
        return this;
    }

    /** Clears all {@code Skill}s of the {@code EditPersonDescriptor}. */
    public EditPersonDescriptorBuilder withSkills() {
        descriptor.setSkills(new HashSet<>());
        return this;
    }

    // ---------------- Back-compat TAG helpers (map to skills) ----------------

    /** Back-compat: sets tags by mapping to skills. */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Skill> skillSet = Arrays.stream(tags)
                .map(Skill::new)
                .collect(Collectors.toSet());
        descriptor.setSkills(skillSet);
        return this;
    }

    /** Back-compat: clears tags by clearing skills. */
    public EditPersonDescriptorBuilder withTags() {
        descriptor.setSkills(new HashSet<>());
        return this;
    }

    /** Builds and returns the {@code EditPersonDescriptor}. */
    public EditPersonDescriptor build() {
        return descriptor;
    }
}

