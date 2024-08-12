import java.io.*;
import java.net.*;
import java.util.Random;


//  The client connects to the server, receives a seed and message count, waits for 10 seconds,
//  sends random numbers to the server, and prints any messages received from the server.
public class TCPClient {
    public static void main(String[] args) throws IOException {
        // Verify correct usage
        if (args.length != 2) {
            System.err.println("Usage: java TCPClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Receive seed from the server
            int seed = Integer.parseInt(in.readLine());
            Random random = new Random(seed);

            // Client waits for 10 seconds before sending messages
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            }

            // Generate and send random numbers
            for (int i = 0; i < 10; i++) {  // Assuming 10 messages to send
                int message = random.nextInt();
                out.println(message);
            }

            // Listen for messages from the server (from the other client)
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Message received: " + inputLine);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
