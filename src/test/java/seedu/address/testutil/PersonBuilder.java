package seedu.address.testutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Team;
import seedu.address.model.person.Skills;
import seedu.address.model.tag.Category;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ROLE = "none";
    public static final String DEFAULT_TEAM = "nowhere";
    public static final String DEFAULT_DEPARTMENT = "homeless";
    public static final String DEFAULT_SKILLS = "nothing";


    private Name name;
    private Phone phone;
    private Email email;
    private Department dept;
    private Skills skills;
    private Role role;
    private Team team;
    private Set<Category> categories;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        dept = new Department(DEFAULT_DEPARTMENT);
        role = new Role(DEFAULT_ROLE);
        team = new Team(DEFAULT_TEAM);
        skills = new Skills(DEFAULT_SKILLS);
        categories = new HashSet<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        dept = personToCopy.getDept();
        role = personToCopy.getRole();
        team = personToCopy.getTeam();
        skills = personToCopy.getSkills();
        categories = personToCopy.getCategories();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withCategories(List<Category> categoryArray) {
        this.categories = SampleDataUtil.getCategorySet(categoryArray);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }
    
    /**
     * Sets the {@code Department} of the {@code Person} that we are building.
     */
    public PersonBuilder withDept(String dept) {
        this.dept = new Department(dept);
        return this;
    }


    /**
     * Sets the {@code Role} of the {@code Person} that we are building.
     */
    public PersonBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Team} of the {@code Person} that we are building.
     */
    public PersonBuilder withTeam(String team) {
        this.team = new Team(team);
        return this;
    }

    /**
     * Sets the {@code Skills} of the {@code Person} that we are building.
     */
    public PersonBuilder withSkills(String skills) {
        this.skills = new Skills(skills);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email,
                dept, role, team, skills,
                categories, tags);
    }

}
