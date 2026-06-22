import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;

public class QueueServer {

    public static void main(String[] args) {

        try {

            File file = new File("tasks.txt");

            if (file.exists()) {

                Scanner scanner =
                        new Scanner(file);

                while (scanner.hasNextLine()) {

                    String task =
                            scanner.nextLine();

                    TaskQueue.addTask(task);
                }

                scanner.close();

                System.out.println(
                        "Recovered Tasks From File");
            }

            ServerSocket serverSocket =
                    new ServerSocket(8000);

            System.out.println(
                    "Queue Server running on port 8000");

            while (true) {

                Socket socket =
                        serverSocket.accept();

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()));

                PrintWriter writer =
                        new PrintWriter(
                                socket.getOutputStream(),
                                true);

                String message =
                        reader.readLine();

                if (message.startsWith("ADD")) {

                    String task =
                            message.substring(4);

                    TaskQueue.addTask(task);

                    FileWriter fileWriter =
                            new FileWriter(
                                    "tasks.txt",
                                    true);

                    fileWriter.write(task + "\n");

                    fileWriter.close();

                    writer.println("TASK_ADDED");
                }

                else if (message.equals("GET")) {

                    String task =
                            TaskQueue.getTask();

                    if (task == null) {

                        writer.println("NO_TASK");
                    }
                    else {

                        writer.println(task);
                    }
                }

                else if (message.equals("SIZE")) {

                    writer.println(
                            TaskQueue.size());
                }

                else if (message.startsWith("ACK")) {

                    String task =
                            message.substring(4);

                    TaskQueue.acknowledgeTask(task);

                    System.out.println(
                            "Acknowledged: " + task);

                    System.out.println(
                            "Queue Size: "
                                    + TaskQueue.size());

                    writer.println(
                            "ACK_RECEIVED");
                }
                socket.close();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}