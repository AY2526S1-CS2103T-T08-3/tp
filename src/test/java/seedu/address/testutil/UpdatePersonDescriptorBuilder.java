package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Skill;

/**
 * A utility class to help with building {@code UpdatePersonDescriptor} objects.
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
     * Returns an {@code UpdatePersonDescriptorBuilder} with fields containing {@code person}'s details.
     */
    public UpdatePersonDescriptorBuilder(Person person) {
        descriptor = new UpdatePersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setCategories(person.getCategories());
        // Use skills (replacement for legacy tags).
        descriptor.setSkills(person.getSkills());
    }

    /** Sets the {@code Name} of the {@code UpdatePersonDescriptor} that we are building. */
    public UpdatePersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /** Sets the {@code Phone} of the {@code UpdatePersonDescriptor} that we are building. */
    public UpdatePersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /** Sets the {@code Email} of the {@code UpdatePersonDescriptor} that we are building. */
    public UpdatePersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    // ---------------- SKILL helpers ----------------

    /** Sets the {@code Skill}s of the {@code UpdatePersonDescriptor} we are building. */
    public UpdatePersonDescriptorBuilder withSkills(String... skills) {
        Set<Skill> skillSet = Arrays.stream(skills)
                .map(Skill::new)
                .collect(Collectors.toSet());
        descriptor.setSkills(skillSet);
        return this;
    }

    /** Clears all {@code Skill}s of the {@code UpdatePersonDescriptor}. */
    public UpdatePersonDescriptorBuilder withSkills() {
        descriptor.setSkills(new HashSet<>());
        return this;
    }

    // ---------------- Back-compat TAG helpers (map to skills) ----------------

    /** Back-compat: sets tags by mapping to skills. */
    public UpdatePersonDescriptorBuilder withTags(String... tags) {
        Set<Skill> skillSet = Arrays.stream(tags)
                .map(Skill::new)
                .collect(Collectors.toSet());
        descriptor.setSkills(skillSet);
        return this;
    }

    /** Back-compat: clears tags by clearing skills. */
    public UpdatePersonDescriptorBuilder withTags() {
        descriptor.setSkills(new HashSet<>());
        return this;
    }

    /** Builds and returns the {@code UpdatePersonDescriptor}. */
    public UpdatePersonDescriptor build() {
        return descriptor;
    }
}

