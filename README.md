# Lister

Lister is a task-tracker app designed to be simple, fast, and very quick to navigate.
Other apps that fulfill this role often are too complicated with different features, resulting in additional time being taken to read the information, 
create new tasks, or otherwise use the app. The local Java application can have its data synced with the [Lister Web App](https://github.com/jamesL103/ListerWeb) to allow data to be synced across devices.

This project is still in progress and has many more features that need to be implemented, but has basic functionalities implemented.

# Syncing with the server

Go to the main page of the deployed [Lister Web App](https://github.com/jamesL103/ListerWeb), and generate your user ID. This is what uniquely identifies your data, so don't lose it.

In the Lister Java App, click the "Activate Sync" button, and enter your user ID. This will load your data from the server (if any).

To save your current app data, click the "Save changes to server" button to save your current app data in the server.

# Building from Source

In the project directory use `./gradlew.bat build` on Windows or `./gradew build` on Linux in the command line to build the project. The JAR file will be in `[project Directory]/app/build/libs`. 
