import java.io.*;
import java.net.*;
import java.util.*;


//  The server initializes a random number generator, accepts connections from two clients,
//  sends them a configuration message, and then relays messages between the two clients.
public class TCPServer {
    private static final int MAX_CLIENTS = 2;
    private static List<ServerThread> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // Verify correct usage
        if (args.length != 3) {
            System.err.println("Usage: java TCPServer <port number> <seed> <number of messages>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        int seed = Integer.parseInt(args[1]);

        // Validate port number range
        if (portNumber <= 1024 || portNumber > 65535) {
            System.err.println("Port number must be between 1025 and 65535");
            System.exit(1);
        }

        Random random = new Random(seed);

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server is listening on port " + portNumber);

            // Accept two clients
            while (clients.size() < MAX_CLIENTS) {
                Socket clientSocket = serverSocket.accept();
                ServerThread clientThread = new ServerThread(clientSocket, random.nextInt());
                clients.add(clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Exception caught when trying to listen on port " + portNumber);
            System.err.println(e.getMessage());
        }
    }

    // ServerThread handles interaction with a client
    static class ServerThread extends Thread {
        private Socket socket;
        private int clientSeed;

        public ServerThread(Socket socket, int clientSeed) {
            this.socket = socket;
            this.clientSeed = clientSeed;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send the seed and number of messages to client
                out.println(clientSeed);

                // Relay messages received from one client to the other
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    for (ServerThread client : clients) {
                        if (client != this) {
                            client.sendMessage(inputLine);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Exception caught when trying to interact with a client");
                System.err.println(e.getMessage());
            }
        }

        // Send a message to this client
        public void sendMessage(String message) {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);
            } catch (IOException e) {
                System.err.println("Error when sending a message to the client");
                e.printStackTrace();
            }
        }
    }
}

