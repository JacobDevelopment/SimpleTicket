

# <img src="src/main/resources/io/jacobking/simpleticket/icons/icon.png" width="100" height=""> Simple Ticket

-------------------------

![Static Badge](https://img.shields.io/badge/Version-1.0.0--beta-blue) ![Static Badge](https://img.shields.io/badge/Developer-JacobDevelopment-green)
![Static Badge](https://img.shields.io/badge/Pull%20Requests-Welcome-red) ![Static Badge](https://img.shields.io/badge/Language-Java-darkgreen) ![Static Badge](https://img.shields.io/badge/JDK-17-blue) ![Static Badge](https://img.shields.io/badge/JavaFX%20SDK-20.0.2-blue) ![Static Badge](https://img.shields.io/badge/jOOQ-3.18.6-darkred) ![Static Badge](https://img.shields.io/badge/Database%20(SQLite--JDBC)-3.42.0.0-red) ![Static Badge](https://img.shields.io/badge/Gradle-8.4-yellow)


## Description


A lightweight, open-sourced and user-friendly IT support ticket management application, designed to be simple and effective for logging and managing IT support-related tickets directly on your local machine. This solution helps streamline and enhance productivity for those who do not have access to large-scale ticketing systems. 

## Installation


> [!IMPORTANT]
> As of right now, the installation has only been tested on a Windows 10 environment. No cross-platform checks have been tested. If anyone is willing to test it on their own environment, please let me know. 

Head over to the latest releases, or click <a href="https://github.com/JacobDevelopment/SimpleTicket/tags">here.</a> Find the latest release denoted from the top, or download older versions. Right now we are standing at **1.0.0-beta.**

Download the .msi installer, run it, select the directory you want the installation to store the files at. From there a shortcut and start-menu program will be created. Run the SimpleTicket.exe, and all of the necessary components will automatically be created in your user directory. 

SimpleTicket utilizes the lightweight SQLite database for easy local storage to house your tickets and user management. It creates a basic database file and properties file to store configurations. 

## Current Features
As of right now, there are two core features of SimpleTicket. This includes creating tickets, and managing the workspace. This includes multiple companies, employees, and departments (per company basis). Regarding the ticket system, you can add comments to each ticket and other additional information. Checkout the interfaces below.
<details>
    <summary>Ticket Interface</summary>
    - <img src="images/Tickets%20Image.png" alt="Ticket Image"/>
    - <img src="images/Ticket%20Viewer.png" alt="Ticket Viewer"/>
</details>

<details>
    <summary>Management Interface</summary>
    <img src="images/Management%20Image.png" alt="Management Interface"/>
</details>

### Tools used:
- SceneBuilder to help develop the UI.
- IntelliJ Ultimate

## License

To see the license, click <a href="https://github.com/JacobDevelopment/SimpleTicket/blob/development/LICENSE">here</a>.

## Changelog
- None currently.

## Roadmap
### 1.0.0 (Production w/ Potential Implementations)
- [X] Dialog/Alert System
- [ ] Implement logging system.
- [X] UI Improvements/Changes/Different theming.
  - [X] Implement ControlsFX for better tools.
  - [X] Use SearchableComboBox.
- [ ] Work on settings page. 
  - [ ] Allow users to target custom working path & custom database.
  - [ ] Implement configuration/settings table in database. 