

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
Meanwhile, the compiled output files will be generated in the `bin` folder by default.


`src` contains two .java files:
- `TCPClient.java`: The client connects to the server, receives a seed and message count, waits for 10 seconds, sends random numbers to the server, and prints any messages received from the server.
In the `TCPClient` code, the client sends a series of random numbers to the server after waiting for 10 seconds. It also listens for messages from the server, which could be relayed messages from the other client.

- `TCPServer.java`: The server initializes a random number generator, accepts connections from two clients, sends them a configuration message, and then relays messages between the two clients.
In the `TCPServer` code, each client runs in its thread, allowing the server to handle multiple clients simultaneously. The server relays messages received from one client to the other.