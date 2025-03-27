# FitFriends User Guide

FitFriends is a **desktop app for sporty young adults** to manage their contacts. It is optimized for use via a **Command Line Interface (CLI)** while still providing the benefits of a Graphical User Interface (GUI). If you can type fast, FitFriends lets you complete contact management tasks faster than traditional GUI apps. It also helps users arrange sport meet-ups and training sessions by keeping track of the sports played by each friend, and by storing addresses (including postal codes) so you can choose the optimal sports facility for a meet-up.

---

## Table of Contents

1. [Quick Start](#quick-start)
2. [Features](#features)
    1. [Viewing help](#viewing-help)
    2. [Adding a person](#adding-a-person)
    3. [Listing all persons](#listing-all-persons)
    4. [Editing a person](#editing-a-person)
    5. [Locating persons by name](#locating-persons-by-name)
    6. [Deleting a person](#deleting-a-person)
    7. [Clearing all entries](#clearing-all-entries)
    8. [Adding a sport](#adding-a-sport)
    9. [Deleting a sport](#deleting-a-sport)
    10. [Finding a sport](#finding-a-sport)
    11. [Sorting by location and sport](#sorting-by-location-and-sport)
    12. [Creating groups](#creating-groups)
3. [Command Summary](#command-summary)

---

## Quick Start

1. **Ensure you have Java 17 or above** installed on your computer.
    - *Mac users:* Ensure you have the precise JDK version prescribed [here](https://www.oracle.com/java/technologies/downloads/).

2. **Download** the latest `.jar` file from the releases page (for example, from your project repository).

3. **Copy** the `.jar` file to the folder you want to use as the *home folder* for FitFriends.

4. **Open a command terminal**, `cd` into the folder you put the jar file in, and use:
   java -jar FitFriends.jar

5. **Type commands** into the command box and press <kbd>Enter</kbd> to execute them.
- Typing `help` and pressing <kbd>Enter</kbd> will show you instructions for using the app.

---

## Features

### Viewing help
Shows a message explaining how to access the help page.

**Format**:
help

---

### Adding a person
Adds a new person to the address book.

**Format**:
  add n/{NAME} p/{NUMBER} e/{EMAIL} a/{ADDRESS} t/{TAG} s/{SPORT} pc/{POSTALCODE}
**Notes**:
- Only **valid postal codes** are allowed.
- Only **valid sports** are allowed.
- A person can have any number of tags (including 0).
- **Example**:
  add n/John Doe p/98765432 e/johnd@example.com a/John Street, #01-01 t/friend s/basketball pc/123456


---

### Listing all persons
Shows a list of all persons in the address book.

**Format**:
list


---

### Editing a person
Edits an existing person in the address book.

**Format**:
  edit {INDEX} [n/{NAME}] [p/{NUMBER}] [e/{EMAIL}] [a/{ADDRESS}] [t/{TAG}] [pc/{POSTALCODE}]
**Notes**:
- You **cannot edit a sport** directly with this command. To edit sports, first remove them using `deletesport`, then add new ones using `addsport`.
- The index refers to the position shown in the current list (e.g., `edit 2` edits the 2nd person in the displayed list).
- **Example**:
  edit 2 n/James Lee p/98765432 a/45 Sunset Blvd pc/654321


---

### Locating persons by name
Finds persons whose names contain any of the specified keywords.

**Format**:
  find {KEYWORD}
  You can provide multiple keywords (space-separated) to perform an OR search.
- **Notes**:
- The search is **case-insensitive**.
  e.g. `hans` will match `Hans`.
- The order of the keywords does not matter.
  e.g. `Hans Bo` will match `Bo Hans`.
- Only the **name** is searched.
- Only **full words** will be matched.
  e.g. `Han` will not match `Hans`.
- Persons matching **at least one** keyword will be returned (OR search).
  e.g. `Hans Bo` will return `Hans Gruber` and `Bo Yang`.

- **Example**:
  find james jake

---

### Deleting a person
Deletes the specified person from the address book.

**Format**:
    delete {INDEX}
**Example**:
    delete 3

---

### Clearing all entries
Clears all entries from the address book.

**Format**:
    clear

---

### Adding a sport
Adds a sport to an existing person in the address book.

**Format**:
    addsport {INDEX} s/{SPORT}
**Notes**:
- Only **one sport can be added** per command.
- Only **valid sports** are allowed.
- **Example**:
    addsport 2 s/basketball

---

### Deleting a sport
Deletes a sport from the specified person in the address book.

**Format**:
    deletesport {INDEX} s/{SPORT}
**Example**:
    deletesport 2 s/basketball

---

### Finding a sport
Filters (or searches) persons by the sports they have.

**Format**:
  findsport s/{SPORT} [SPORT] [SPORT]
**Notes**:
- You may include **multiple sports** (separated by spaces).
- Contacts who play **any** of the sports listed will be shown (OR search).
- **Example**:
  findsport s/basketball tennis(Shows all contacts who play either basketball **or** tennis.)

---

### Sorting by location and sport
An extension of the `findsport` feature that allows you to filter by one or more sports **and** provide a reference postal code to sort results by distance.

**Format**:
  findsport pc/{POSTALCODE} s/{SPORT} [SPORT] [SPORT]

**Notes**:
- If a postal code is provided, the matching contacts will be sorted by their distance from that postal code.
- Contacts who play **any** of the listed sports will appear.
- **Example**:
  findsport pc/123456 s/tennis hockey
  (Shows all who play tennis or hockey, **sorted** by distance from postal code `123456`.)

---

### Creating groups
Creates a group that you can add contacts into (group management commands forthcoming).

**Format**:
  creategroup g/{GROUP NAME}
**Example**:
    creategroup g/tennisbuddies

---

## Command Summary

| **Action**                  | **Format**                                                             | **Example**                                                                                   |
|-----------------------------|-------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|
| **Viewing help**            | `help`                                                                 | `help`                                                                                       |
| **Adding a person**         | `add n/{NAME} p/{NUMBER} e/{EMAIL} a/{ADDRESS} t/{TAG} s/{SPORT} pc/{POSTALCODE}` | `add n/John Doe p/98765432 e/johnd@example.com a/John St t/friend s/tennis pc/123456`         |
| **Listing all persons**     | `list`                                                                 | `list`                                                                                       |
| **Editing a person**        | `edit {INDEX} [n/{NAME}] [p/{NUMBER}] [e/{EMAIL}] [a/{ADDRESS}] [t/{TAG}] [pc/{POSTALCODE}]` | `edit 2 n/James Lee p/98765432 a/Block 123 pc/654321`                                        |
| **Locating persons by name**| `find {KEYWORD} [MORE_KEYWORDS]`                                       | `find James Jake`                                                                            |
| **Deleting a person**       | `delete {INDEX}`                                                       | `delete 3`                                                                                   |
| **Clearing all entries**    | `clear`                                                                | `clear`                                                                                      |
| **Adding a sport**          | `addsport {INDEX} s/{SPORT}`                                           | `addsport 1 s/tennis`                                                                        |
| **Deleting a sport**        | `deletesport {INDEX} s/{SPORT}`                                        | `deletesport 2 s/basketball`                                                                 |
| **Finding a sport**         | `findsport s/{SPORT} [SPORT] [SPORT]`                                  | `findsport s/basketball tennis`                                                              |
| **Sorting by location**     | `findsport pc/{POSTALCODE} s/{SPORT} [SPORT] [SPORT]`                  | `findsport pc/123456 s/tennis hockey`                                                        |
| **Creating groups**         | `creategroup g/{GROUP NAME}`                                           | `creategroup g/MarathonBuddies`                                                              |

---



