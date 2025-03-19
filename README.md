[![Java CI](https://github.com/AY2425S2-CS2103T-F12-1/tp/actions/workflows/gradle.yml/badge.svg)](https://github.com/AY2425S2-CS2103T-F12-1/tp/actions/workflows/gradle.yml)

# FitFriends

![Ui](docs/images/Ui.png)

## Setting up in Intellij

Prerequisites: JDK 17, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
2. Open the project into Intellij as follows:
  * Click `Open`.
  * Select the project directory, and click `OK`.
  * If there are any further prompts, accept the defaults.
3. Configure the project to use **JDK 17** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
4. After that, locate the `PLACEHOLDER_FILE_FOR_NOW` file, right-click it, and choose `Run FitFriends.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:

      ```
      Hello from
      FITFRIENDS!!!!!!!!!!!!!!!!!!!!!
      ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
