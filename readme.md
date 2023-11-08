

# <img src="src/main/resources/io/jacobking/simpleticket/icons/icon.png" width="100" height=""> Simple Ticket

-------------------------

![Static Badge](https://img.shields.io/badge/Version-0.1.2--beta-blue) ![Static Badge](https://img.shields.io/badge/Developer-JacobDevelopment-green)
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
### 0.1.2-beta
* Reduce version semantics, not sure why we stated with "1.0.0".
* Adding database upgrade checks depending on the "configuration" database table.
  * This will make previous versions incompatible, likely. But we will see.

### 0.1.1-beta
* Fixed ticket comment system, tickets were posting but not actually being stored in DB.

### 0.1.0-beta
* Dialog/Alert System introduced.
* UI Changes & Improvements.
* Implemented ControlsFX for more convenient controls.
* Database Schema changes.

## Known Bugs
* Updating the department supervisor doesn't update in the table UI.
* SQL query error upon application launch.
* Disabled Confirmation doesn't apply to ticket deletion. (Should we keep or add another toggle switch?)


## Todo(?)
- [ ] Implement auto-archive/auto-delete tickets.
- [ ] Implement ticket exporting (.JSON, CSV?, Unknown)
- [ ] ~~Implement settings > reset to factory button.~~
- [ ] Implement home screen with statistics.
- [ ] Fix main icon badge to match current color schemes.
- [ ] Re-work "portal" screens to be less ugly. (Not creatively inclined.)
- [ ] Work on screen resizing, SimpleTicket does not fill correctly height wise on smaller screens.
