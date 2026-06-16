import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WorkerServer {

    public static void main(String[] args) {

        try {

            if (args.length == 0) {
                System.out.println("Please provide port number.");
                return;
            }

            int port = Integer.parseInt(args[0]);

            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Worker listening on port " + port);

            while (true) {

                Socket socket = serverSocket.accept();

                System.out.println("Master connected!");

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));

                PrintWriter writer =
                        new PrintWriter(socket.getOutputStream(), true);

                String message = reader.readLine();

                System.out.println("Received: " + message);

                String[] parts = message.split(" ");

                int start = Integer.parseInt(parts[1]);
                int end = Integer.parseInt(parts[2]);

                int sum = 0;

                for (int i = start; i <= end; i++) {
                    sum += i;
                }

                System.out.println("Calculated Result: " + sum);

                writer.println(sum);

                reader.close();
                writer.close();
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}