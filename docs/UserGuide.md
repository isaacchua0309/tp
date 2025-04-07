---
layout: page
title: FitFriends User Guide
---
# FitFriends User Guide
*Last Updated: 7 Apr 2025*

<img src="images/fitfriends_logo.png" alt="FitFriends Logo" width="300" height="300"/>

## 🏆 Our Mission

FitFriends is designed to help **sporty young adults** connect, organize and participate in sports activities together. In today's busy world, finding friends with similar sporting interests and coordinating meetups can be challenging. FitFriends makes this process seamless and enjoyable.

*refers to adults between 16-35 years of age and are active or looking to get active in the sporting scene

### 🎯 Problems We're Solving

**Primary Challenge:**
- Difficulty in organizing sports meetups with friends who share similar interests in specific locations

**Secondary Challenges:**
- Remembering which friends play which sports
- Finding suitable locations for sports activities based on everyone's location
- Discovering conversation topics related to sports interests

### 💪 How FitFriends Helps You

- **Sports-focused contact management:** Keep track of which friends play which sports
- **Location-based planning:** Sort friends by proximity to a specific location using postal codes
- **Game organization:** Create games for different sports or friend circles
- **Smart recommendations:** Find the perfect sports partners based on interests and location

> 📊 **Did you know?** According to our surveys, 78% of young adults want to try new sports but don't know who to play with. FitFriends helps you discover which friends might be interested in picking up new sports like tennis or pickleball together!

---

## 📋 Table of Contents


- <a href="#getting-started">️️Getting Started</a>
    - [System Requirements](#system-requirements)
    - [Installation](#installation)
    - [First Launch](#first-launch)
- <a href="#using-fitfriends">️️Using FitFriends</a>
    - [Interface Overview](#interface-overview)
    - [Command Format](#command-format)
- <a href="#basic-features">️️Basic Features</a>
    - [Viewing Help](#viewing-help)
    - [Managing Contacts](#managing-contacts)
    - [Sports Management](#sports-management)
    - [Game Management](#game-management)
- <a href="#command-summary">Command Summary</a>
- <a href="#faqs">️️Faqs</a>
- <a href="#troubleshooting">️️Troubleshooting</a>
- <a href="#references">️️References</a>


---

## <a id="getting-started"></a>💻 Getting Started

### System Requirements

- **Operating System:** Windows 10/11, macOS 10.15+, or Linux
- **Java:** Version 17 or above ([Download here](https://www.oracle.com/java/technologies/downloads/))
- **Storage:** Minimum 100MB free space
- **RAM:** Minimum 2GB (4GB recommended)

### Installation

1. **Download** the latest `FitFriends.jar` file from our [releases page](https://github.com/AY2425S2-CS2103T-F12-1/tp/releases).

2. **Create a folder** where you want to store FitFriends data.

3. **Move** the downloaded `.jar` file to this folder.

### First Launch

#### Using the Command Line Interface (CLI)
###### Learn more about CLI [here](https://www.techtarget.com/searchwindowsserver/definition/command-line-interface-CLI).

1. **Open a terminal/command prompt**
    - Windows: `Win + R` > type `cmd`
    - macOS: `Cmd + Space` > type `terminal`
    - Linux: `Ctrl + Alt + T`

2. **Navigate to folder**:
   ```
   cd path/to/your/fitfriends/folder
   ```

3. **Launch the application**:
   ```
   java -jar FitFriends.jar
   ```

4. **You should see** the FitFriends loading screen. Congratulations! You're ready to start.

#### Using the Graphical Interface

1. **Double-click** on the FitFriends.jar file.

   > ⚠️ **If this doesn't work**: Your system might not have associated .jar files with Java. Use the CLI method above or right-click the file and select "Open with Java".

2. **Enter commands** in the command box at the bottom of the window and press <kbd>Enter</kbd> to execute them.

![FitFriends Main UI](images/main_ui.png)

---
## <a id="using-fitfriends"></a> 🏋️️ Using FitFriends
### Interface Overview

FitFriends combines the power of a Command Line Interface (CLI) with the convenience of a Graphical User Interface (GUI):

- **Command Box**: Type your commands here and press <kbd>Enter</kbd> to execute
- **Result Display**: Shows the result of your command
- **Friend List**: Displays all your contacts with their information
- **Person Card**: Shows detailed information for each contact

![Interface Components](images/interface.png)

### Command Format

- Words in `UPPERCASE` are parameters to be supplied by you.
- Items in square brackets `[...]` are optional.
- Items with `...` after them can have multiple instances.
- Parameters can be in any order.

> 💡 **Tip**: Command words are case-sensitive, so you should only type `help`, and not `HELP`, `HeLp` or even `Help`.

---
## <a id="basic-features"></a> 🌟 Basic Features

### Viewing Help

Need assistance? The help command gives you quick access to instructions.

**Format**:
```
help
```

When you type `help` and press <kbd>Enter</kbd>, you'll see a message with a link to this user guide.

![Help Command](images/help_command.png)

---

### Managing Contacts

#### Adding a Friend

Add your sports friends to your FitFriends list with details that matter for planning activities.

**Format**:
```
add n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG] s/SPORT pc/POSTALCODE
```

**Example**:
```
add n/John Doe p/98765432 e/johnd@example.com a/John Street, #01-01 t/bestfriend s/tennis pc/123456
```

This adds John Doe to your list of friends with his details and shows he plays tennis.

![Adding a Friend](images/add_command.png)

> ⚠️ **Notes**:
> * Name: Names are case-insensitive (e.g., the names, Somneel and somneel, are the same and will not be allowed when adding a contact).
> * Adding a friend places them in the alphabetically sorted position in the list.
> * "s/o" will not be accepted as part of a name, we recommend you enter this part of your friend's name as "s_o", "so" or even "s o" instead.
> * Postal Code: Only valid Singapore postal codes are accepted.
> * Sport: Only valid sports from "Valid Sports List" are allowed. You can add valid sports to this list by using the `createsport` command.
> * Tag: Tags are optional. You can add as many tags as you want to a contact (e.g., `t/bestfriend t/colleague`). Tags help you categorize your contacts.

#### Listing All Friends

View all your friends in one place.

**Format**:
```
list
```

This displays all your friends in the left panel, sorted by alphabetical order.

![Listing All Friends](images/list_command.png)

> 💡 **List Tips**:
> * You should run this command after adding, deleting or editing friends to see the updated list.
> * You should also run this command after running find or findsport commands to view the full list.

#### Editing a Friend

Update your friend's details as they change.

**Format**:
```
edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG] [pc/POSTALCODE]
```

**Example**:
```
edit 2 n/James Lee p/87654321 a/45 Sunset Blvd pc/018907
```

This updates the 2nd friend in your current FitFriends list with a new name, phone number, address, and postal code.

![Editing a Friend](images/edit_command.png)

> 💡 **Edit Tips**:
> * Editing a tag will remove all existing tags as tags cannot be added sequentially (e.g., `edit t/bestfriend` followed by `edit t/colleague` will result in only the `colleague` tag remaining).
> * Adding a friend places them in the alphabetically sorted position in the list.
> * "s/o" will not be accepted as part of a name, we recommend you enter this part of your friend's name as "s_o", "so" or even "s o" instead.

#### Locating Friends by Name

Quickly find friends by searching for part of their name.

**Format**:
```
find KEYWORD [MORE_KEYWORDS]
```

**Example**:
```
find james jake
```

This will show all friends whose names contain "James" or "Jake".

![Finding Friends](images/find_command.png)

> ⚠️ **Note**:
> - Search keywords are case-insensitive
> - Order of keywords doesn't matter
> - Only full words are matched (e.g., "Han" won't match "Hans")
> - Any contact matching at least one keyword will be shown

> 💡 **Search Tip**: If you run edit, delete, addsport, deletesport commands immediately after this command, these commands will apply to the contact listed at the index as shown by the find command. Use this to manage your contacts without having to search for them manually!


#### Deleting a Friend

Remove a friend from your FitFriends list.

**Format**:
```
delete INDEX
```

**Example**:
```
delete 3
```

This removes the 3rd friend in your current FitFriends list.

![Deleting a Friend](images/delete_command.png)

#### Clearing All Entries

Start fresh by removing all friends from your FitFriends list.

**Format**:
```
clear
```

> ⚠️ **Warning**: This action cannot be undone. All your contacts will be permanently removed.

![Clearing All Entries](images/clear_command.png)

---

### Sports Management

#### Creating a Sport
Add new sports to the global sports list for all contacts to use.

**Format**:
```
createsport s/SPORT_NAME
```

**Example**:
```
createsport s/archery
```

This adds archery to the global sports list that can be assigned to contacts.

![Creating a Sport](images/createsport_command.png)

> ⚠️ **Note**:
> * Sport names entered in commands are case-insensitive and will be stored and displayed in lowercase.

#### Listing All Sports
View all available sports in the global sports list with their indices.

**Format**:
```
listsports
```

This displays all sports in alphabetical order with numbers for reference.

![Listing All Sports](images/listsports_command.png)

> 💡 **Tip**: Take note of the indices as they're needed when deleting sports from the global list.

#### Deleting a Sport from Global List of Sports

Remove a sport from the global sports list.

**Format**:
```
deletesport INDEX
```

**Example**:
```
deletesport 1
```

This removes the first sport in the alphabetically sorted global sports list.

![Deleting a Sport globally](images/deletesport_global_command.png)

> ⚠️ **Warning**: Removing a sport from the global list means it can no longer be assigned to contacts.

#### Adding a Sport

Record which sports your friends play to make planning activities easier.

**Format**:
```
addsport INDEX s/SPORT
```

**Example**:
```
addsport 2 s/basketball
```

This adds basketball to the list of sports played by the 2nd friend in your current FitFriends list.

![Adding a Sport to a friend](images/addsport_command.png)

> 💡 **Add Sport Tip**: You can only add one sport at a time. For multiple sports, use the command several times.

#### Deleting a Sport

Update your FitFriends list if a friend no longer plays a particular sport.

**Format**:
```
deletesport INDEX s/SPORT
```

**Example**:
```
deletesport 2 s/basketball
```

This removes basketball from the 2nd friend's list of played sports.

![Deleting a Sport from a friend](images/deletesport_command.png)

#### Finding Friends by Sport

Discover which friends play specific sports—perfect for organizing game days!

**Format**:
```
findsport s/SPORT [s/SPORT]
```

**Example**:
```
findsport s/basketball tennis
```

This shows all friends who play either basketball or tennis (or both).

![Finding Friends by Sport](images/findsport_command.png)

> 💡 **Pro-tip**: If you run edit, delete, addsport, deletesport commands immediately after this command, these commands will apply to the contact listed at the index as shown by the findsport command. Use this to manage your contacts without having to search for them manually!

#### Finding Friends by Sport and Location

Find sports friends near a specific location—ideal for planning convenient meetups!

**Format**:
```
findsport pc/POSTALCODE s/SPORT [s/SPORT]
```

**Example**:
```
findsport pc/259366 s/tennis s/hockey
```

This shows all friends who play tennis or hockey, sorted by their proximity to postal code 259366.

![Finding by Sport and Location](images/findsport_location_command.png)

> ⚠️ **Note**:
> * Searche keywords are case-insensitive
> * Proximity is calculated by contact's postal code
> * More than 1 sport can be included in the search by adding s/SPORT_NAME

> 💡 **Find Sport Tips**:
> * Use this feature to find the optimal meeting spot for your sports game!
> * If you run edit, delete, addsport, deletesport commands immediately after this command, these commands will apply to the contact listed at the index as shown by the findsport command. Use this to manage your contacts without having to search for them manually!

---

#### Game Management

#### Adding Games

Organize your sports friends into games for different activities or events.

**Format**:
```
addgame g/SPORT_NAME dt/DATE_TIME pc/POSTAL_CODE
```

**Example**:
```
addgame g/badminton dt/2025-04-01T15:00:00 pc/259366
```

This creates a new game called "badminton".


![Adding a Game](images/addgame_command.png)


#### Deleting Games

Remove Games you no longer need.

**Format**:
```
deletegame g/INDEX
```

**Example**:
```
deletegame g/1
```

This deletes the "badminton" game.


![Deleting a Game](images/deletegame_command.png)


### Adding Members

Adds friends as members to a particular game

**Format**:
```
addmember g/INDEX n/PERSON_NAME
```

**Example**:
```
addmember g/1 n/Mary Jane
```

### Deleting Members

**Format**:

Deletes friends as members from a particular game
```
deletemember g/INDEX n/PERSON_NAME
```

**Example**:
```
deletegame g/1 n/Mary Jane
```

### Editing Location of Existing Game

**Format**:

Changes the location of an existing game
```
editgamelocation g/INDEX pc/POSTAL_CODE
```

**Example**:
```
editgamelocation g/1 pc/119077
```
---

## <a id="command-summary"></a>📝 Command Summary

| **Action**                   | **Format**                                                                    | **Example**                                                                           |
|------------------------------|-------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| **Help**                     | `help`                                                                        | `help`                                                                                |
| **Add Friend**               | `add n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG] s/SPORT pc/POSTALCODE`          | `add n/John Doe p/98765432 e/johnd@example.com a/John St t/friend s/tennis pc/123456` |
| **List Friends**             | `list`                                                                        | `list`                                                                                |
| **Edit Friend**              | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG] [pc/POSTALCODE]` | `edit 2 n/James Lee p/98765432 a/Block 123 pc/654321`                                 |
| **Find by Name**             | `find KEYWORD [MORE_KEYWORDS]`                                                | `find James Jake`                                                                     |
| **Delete Friend**            | `delete INDEX`                                                                | `delete 3`                                                                            |
| **Clear All**                | `clear`                                                                       | `clear`                                                                               |
| **Create Sport**             | `createsport s/SPORT_NAME`                                                    | `createsport s/archery`                                                               |
| **List Sports**              | `listsports`                                                                  | `listsports`                                                                          |
| **Delete Global Sport**      | `deletesport INDEX`                                                           | `deletesport 1`                                                                       |
| **Add Sport**                | `addsport INDEX s/SPORT`                                                      | `addsport 1 s/tennis`                                                                 |
| **Delete Sport**             | `deletesport INDEX s/SPORT`                                                   | `deletesport 2 s/basketball`                                                          |
| **Find by Sport**            | `findsport s/SPORT [s/SPORT]`                                                 | `findsport s/basketball s/tennis`                                                     |
| **Find by Sport & Location** | `findsport pc/POSTALCODE s/SPORT [s/SPORT]`                                   | `findsport pc/018907 s/tennis s/hockey`                                                 |
| **Add Game**                 | `addgame g/SPORT_NAME dt/DATE_TIME pc/POSTAL_CODE`                            | `addgame g/volleyball dt/2025-04-04T15:30:00 pc/259366`                               |
| **Delete Game**              | `deletegame g/INDEX`                                                          | `deletegame g/1`                                                                      |
| **Add Member**               | `addmember g/INDEX n/PERSON_NAME`                                             | `addmember g/1 n/Alice Pauline`                                                       |
| **Delete Member**            | `deletemember g/INDEX n/PERSON_NAME`                                          | `deletemember g/1 n/Alice Pauline`                                                    |
| **Edit Game Location**            | `editgamelocation g/INDEX pc/POSTAL_CODE`                                          | `editgamelocation g/1 pc/119077`                                                    |



---
## <a id="faqs"></a>🙋 FAQs

**Q: Can I use FitFriends to find sports facilities?**
A: Currently, FitFriends doesn't directly provide information about sports facilities, but it helps you coordinate with friends who live near specific locations by using findsport with postal codes.

**Q: How many sports can I add per contact?**
A: There is no limit! Add as many sports as your friend plays.

**Q: Can I import contacts from my phone?**
A: This feature is coming in our next update. Stay tuned!

---
## <a id="troubleshooting"></a>🛠️ Troubleshooting

**Problem: FitFriends won't start**
- Verify you have Java 17+ installed by running `java -version` in your terminal, if the version is correct, a message like this should appear: `java version "17.0.13" 2024-10-15 LTS`
- Try running FitFriends from the command line to see error messages and describe your problem to a generative AI model like ChatGPT.

**Problem: Command not recognized**
- Check your spelling and format
- Refer to the Command Summary table for correct syntax

**Stil need help?** Visit our [support page](https://github.com/AY2425S2-CS2103T-F12-1/tp/issues) or email `fitfriends.support@gmail.com`.

---
## <a id="references"></a>📚 References

1. [Benefits of sports in social bonding](https://www.ncbi.nlm.nih.gov/pmc/articles/PMC6125028/)
2. [The importance of location in sports participation](https://www.tandfonline.com/doi/full/10.1080/16184742.2019.1566931)
3. [Java documentation](https://docs.oracle.com/en/java/)

---

[Back to Top](#fitfriends-user-guide)
