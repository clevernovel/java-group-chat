# Group Chat Application

This project is a simple client-server group chat application implemented in Java. It allows multiple clients to connect to a server and communicate with each other in real-time. The server handles client connections and broadcasts messages to all connected clients.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)

## Features

- Multi-client support: Multiple clients can connect to the server and chat with each other.
- Real-time messaging: Messages are broadcasted to all connected clients instantly.
- Client handler: Each client connection is handled in a separate thread.
- Logging: Logs important events and errors for easier debugging and monitoring.

## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/group-chat-app.git
   
2. Navigate to the project directory:
   ```sh
   cd group-chat-app
3. Build the project using Maven:
   ```sh
   mvn clean install
   
## Usage

1. Start the server:
   ```sh
   - java -cp target/group-chat-app-1.0-SNAPSHOT.jar main.java.dev.clevernovel.groupchat.server.Server
2. Start a client:
   ```sh
   - java -cp target/group-chat-app-1.0-SNAPSHOT.jar main.java.dev.clevernovel.groupchat.client.Client
3. Follow messages in console. 