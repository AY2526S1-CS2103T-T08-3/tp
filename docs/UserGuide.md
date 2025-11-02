---
layout: page
title: User Guide
---
<h1>SlackBook User Guide</h1>

SlackBook is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) that has the benefits of a Graphical User Interface (GUI). It is a productivity tool purpose-built for HR departments to handle the increasing administrative load of a growing company. Experienced HR professionals who are familiar with the tasks they need to perform but whose productivity are bottlenecked by the tools they’re using will stand to benefit the most, as for the fast typers, SlackBook can perform day-to-day contact management tasks done faster than traditional GUI-based apps.

## Table of Contents
* [Quick start](#quick-start)
* [Features](#features)
  * [Viewing Help](#viewing-help--help)
  * [Adding an employee](#adding-an-employee-add_employee)
  * [Listing all employees](#listing-all-employees--list)
  * [Listing by category](#listing-by-category--listbycategory)
  * [Deleting an employee](#deleting-an-employee--delete_employee)
  * [Categorize employees](#categorize-employees--assign_category)
  * [Update employee information](#update-employee-information--update_employee)
  * [Clearing all entries](#clearing-all-entries--clear)
  * [Exiting the program](#exiting-the-program--exit)
  * [Saving the data](#saving-the-data)
  * [Editing the data file](#editing-the-data-file)
* [FAQ](#faq)
* [Known issues](#known-issues)
* [Command summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. **Ensure you have Java `17` or above** installed in your Computer.<br>
   ***Mac users:*** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. **Download** the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-T08-3/tp/releases).

3. **Copy** the file to the folder you want to use as the _home folder_ for SlackBook.

4. Open a command terminal, `cd` into the folder you put the jar file in (e.g. `cd Desktop\slackbook`), and use the `java -jar slackbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)
5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all employees.

   * `add_employee n/John Doe d/Engineering t/Team 3 r/Software Engineer e/john.doe@example.com p/92345678 s/Python` : Adds an employee named `John Doe` to the directory that has the department `Engineering`, is on `Team 3`, has the role of a `Software Engineer`, email `john.doe@example.com`, phone number `92345678`, and have skills in `Python`.

   * `delete_employee 3` : Prompts the confirmation of the deletion of the 3rd employee shown in the current list.

   * `clear` : Deletes all employees.

   * `exit` : Exits the app.
6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add_employee n/NAME`, `NAME` is a parameter which can be used as `add_employee n/John Doe`.

* Items in square brackets [] are optional.<br>
  e.g `n/NAME [s/SKILLS]...` can be used as `n/John Doe s/Java` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[s/SKILLS]…​` can be used as ` ` (i.e. 0 times), `s/Java`, `s/Java s/Python` or omitted entirely.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE`, `p/PHONE n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding an employee: `add_employee`

Adds an employee to the directory.

Format: `add_employee n/NAME [d/DEPARTMENT] [t/TEAM] [r/ROLE] e/EMAIL p/PHONE [s/SKILLS]...`

:bulb: **Tip:**
A person can have any number of skills (including 0).

:bulb: **Tip:**
If you forget to add a category (like department or role), you can use assign_category later.

| Field (Prefix) | Required | Rules & Usage                                                                                                               | Warnings & Examples                                                                                                              |
|----------------|----------|-----------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| Name (n/)      | Yes      | - 1–100 characters<br>- Only letters, spaces, hyphens, and apostrophes allowed<br>- Case-insensitive duplicates not allowed | ⚠ Do not include numbers or symbols (e.g., @, #, !).<br>✅ Example: n/John O’Neill<br>❌ Error: Error: Invalid characters in name. |
| Department (d/)| No       | - Up to 50 characters                                                                                         | ⚠ Keep it short and descriptive.<br>❌ Avoid long labels or special characters.<br>✅ Example: d/Engineering                       |
| Team (t/)      | No       | - Up to 50 characters                                                                                         | ⚠ Use readable names instead of codes.<br>✅ Example: t/Team 3                                                                    |
| Role (r/)      | No       | - Up to 50 characters                                                                                         | ⚠ Long role names may be rejected.<br>✅ Example: r/Software Engineer                                                             |
| Email (e/)     | Yes      | - Must follow `local@domain.com` format                                                                                     | ⚠ Invalid email formats are rejected.<br>✅ Example: e/jane.doe@example.com<br>❌ Error: Error: Invalid email format.              |
| Phone (p/)     | Yes      | - More than 3 digits<br>- Digits only                                                                                       | ⚠ Do not include country code or symbols.<br>✅ Example: p/92345678<br>❌ Error: Error: Invalid phone number.                      |
| Skills (s/)    | No       | - Each ≤ 30 characters                                                              | ✅ Example: s/Python s/Java s/Project Management                                                                                  |


Examples:
* `add_employee n/John Doe d/Engineering t/Team 3 r/Software Engineer e/john.doe@example.com p/92345678 s/Python`
* `add_employee n/Alex Yeo d/HR t/Team A r/Manager e/alex.yeo@example.com p/92345678`

### Listing all employees : `list`

Allow users to view all employees currently stored in the system in a clear and structured format.

Format: `list [s/SKILLS]`

| Field (Prefix) | Required | Rules & Usage                              | Examples                             |
|----------------|----------|-------------------------------------------|-------------------------------------------------|
| Skills (s/)    | No       | - Each ≤ 30 characters                                 | ✅ Example: s/Python s/Java s/Project Management |

Examples:
* `list`
* `list s/java`

### Listing by category : `listbycategory`

Displays a structured summary of all employees in the directory, grouped by a selected **category** — such as their **Role**, **Team**, or **Department**.
This helps users get an overview of how employees are distributed across different parts of the organization.

Format: `listbycategory c/CATEGORY`

| Field (Prefix) | Required | Rules & Usage                                   | Warnings & Examples           |
|----------------|----------|-------------------------------------------------|-------------------|
| Category (c/)  | Yes      | - Must be one of Department, Team, or Role.<br>- Case-insensitive — "department" and "DEPARTMENT" are the same. | ⚠ Only these three terms are allowed.<br>❌ Error: Error: Category must be one of Team/Role/Department.<br>✅ Example: c/Team |


Examples:
* `listbycategory c/role`
* `listbycategory c/team`
* `listbycategory c/department`


### Deleting an employee : `delete_employee`

Deletes the specified employee from the directory.

Format: `delete_employee INDEX`

* Prompts the confirmation of the deletion of the employee at the specified `INDEX`.
* The index refers to the index number shown in the displayed employee list.
* The index **must be a positive integer** 1, 2, 3, …​
* A confirmation prompt will be shown.
* Input `yes` to confirm the deletion.

:bulb: **Tip:**
Use list before deletion to verify the correct employee.

:exclamation: **Caution:**
This action cannot be undone. Once an employee's information is deleted, it cannot be recovered.

Examples:
* `delete_employee 2` prompts the confirmation of the deletion of the 2nd employee in the directory.

### Categorize employees : `assign_category`

Help users to organize employees by department, team, or role for easier navigation.

Format: `assign_category INDEX c/CATEGORY_TYPE v/VALUE`

* Categorize the employee at the specified `INDEX`.
* The index refers to the index number shown in the displayed employee list.
* The index **must be a positive integer** 1, 2, 3, …​

| Field (Prefix)        | Required | Rules & Usage                                                                                                 | Warnings & Examples                                                                                                                                                     |
|----------------------|----------|---------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Index                | Yes      | - Refers to the employee number shown in the last list view.<br>- Must be a positive integer.                 | ⚠ Do not use names or negative numbers.<br>❌ Invalid Example: assign_category John c/Role v/Manager<br>✅ Correct: assign_category 1 c/Role v/Manager                      |
| Category Type (c/)    | Yes      | - Must be one of Department, Team, or Role.<br>- Case-insensitive — "department" and "DEPARTMENT" are the same. | ⚠ Only these three terms are allowed.<br>❌ Error: Error: Category must be one of Team/Role/Department.<br>✅ Example: c/Team                                              |
| Value (v/)            | Yes      | - The category value to assign (e.g., “Engineering”).<br>- Up to 50 characters.<br>- Case-sensitive and must contain only alphanumeric characters or spaces.                           | ⚠ Avoid symbols or empty values.<br>❌ Error: Error: Category/Value should be alphanumeric and spaces.<br>✅ Example: v/Project Phoenix                                     |


Examples:
* `assign_category 2 c/Department v/Engineering` assigns the 2nd employee in the directory with, Department: Engineering.

### Update employee information : `update_employee`

Enable users to modify existing employee information to keep records accurate.

Format: `update_employee INDEX [n/NAME] [d/DEPARTMENT] [t/TEAM] [r/ROLE] [e/EMAIL] [p/PHONE] [s/SKILLS]...`

* Updates the employee at the specified `INDEX`.
* The index refers to the index number shown in the displayed employee list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `update_employee 1 r/Senior Software Engineer d/Product Engineering` updates the 1st person in the directory with, Role: Senior Software Engineer, Department: Product Engineering.
* `update_employee 2 n/Alex Yeo d/IT t/Team A r/Coder e/alexyeo@example.com p/98765432 s/Csharp s/C` updates the 2nd person in the directory with, Name: Alex Yeo, Department: IT, Team: Team A, Role: Coder, Email: alexyeo@example.com, Phone: 98765432, Skills: Csharp, C 

### Clearing all entries : `clear`

Removes all employees and their information from the directory.

:exclamation: **Caution:**
Use this only when you’re absolutely sure — **it removes every entry**. SlackBook automatically saves after clearing.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

Slackbook data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Slackbook data is saved automatically as a JSON file `[JAR file location]/data/slackbook.json`. Advanced users are welcome to update data directly by editing that data file.

:exclamation: **Caution:**
If your changes to the data file make its format invalid, Slackbook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the Slackbook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and replace the file `[JAR file location]/data/slackbook.json` with the file from the same location on your previous computer.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Help** | `help`
**Add** | `add_employee n/NAME [d/DEPARTMENT] [t/TEAM] [r/ROLE] e/EMAIL p/PHONE [s/SKILLS]...​` <br> e.g., `add_employee n/John Doe d/Engineering t/Team 3 r/Software Engineer e/john.doe@example.com p/92345678 s/Python`
**List** | `list [s/SKILLS]`<br> e.g., `list s/java`
**List By Category** | `listbycategory c/CATEGORY`<br> e.g., `listbycategory c/role`
**Delete** | `delete_employee INDEX`<br> e.g., `delete_employee 3`
**Categorize** | `assign_category INDEX c/CATEGORY_TYPE v/VALUE​`<br> e.g.,`assign_category 2 c/Department v/Engineering`
**Update** | `update_employee INDEX [n/NAME] [d/DEPARTMENT] [t/TEAM] [r/ROLE] [e/EMAIL] [p/PHONE] [s/SKILLS]...​`<br> e.g.,`update_employee 2 n/Alex Yeo d/IT t/Team A r/Coder e/alexyeo@example.com p/98765432 s/Csharp s/C`
**Clear** | `clear`
**Exit** | `exit`
