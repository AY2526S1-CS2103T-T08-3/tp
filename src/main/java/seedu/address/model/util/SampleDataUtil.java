package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Skill;
import seedu.address.model.tag.Category;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    categories("Engineering", "Alpha", "Software Engineer"),
                    getSkillSet("python", "java")),

            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    categories("Engineering", "Alpha", "Project Manager"),
                    getSkillSet("java")),

            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    categories("Human Resources", "Bravo", "HR Executive"),
                    getSkillSet("csharp")),

            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    categories("Marketing", "Bravo", "Marketing Executive"),
                    getSkillSet("csharp")),

            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    categories("Engineering", "Alpha", "QA Tester"),
                    getSkillSet("sql")),

            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    categories("Finance", "Charlie", "Accountant"),
                    getSkillSet("sql"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a category set containing the list of categories given.
     */
    public static Set<Category> getCategorySet(List<Category> categoryList) {
        return categoryList.stream().collect(Collectors.toSet());
    }

    /**
     * Returns a skill set containing the list of strings given.
     */
    public static Set<Skill> getSkillSet(String... strings) {
        return Arrays.stream(strings)
                .map(Skill::new)
                .collect(Collectors.toSet());
    }

    /**
     * Helper to build a Category set for Department, Team, and Role.
     * Null/blank inputs are ignored.
     */
    private static Set<Category> categories(String department, String team, String role) {
        Set<Category> set = new HashSet<>();
        if (department != null && !department.isBlank()) {
            set.add(new Category("Department", department));
        }
        if (team != null && !team.isBlank()) {
            set.add(new Category("Team", team));
        }
        if (role != null && !role.isBlank()) {
            set.add(new Category("Role", role));
        }
        return set;
    }
}

