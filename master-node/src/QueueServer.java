import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class QueueServer {

    public static void main(String[] args) {

        try {

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

                    writer.println("TASK_ADDED");
                }
                else if (message.equals("GET")) {

                    String task = TaskQueue.getTask();

                    if (task == null) {

                        writer.println("NO_TASK");
                    }
                    else {

                        writer.println(task);
                    }
                }

                socket.close();
            }
        }

        catch (Exception e) {

            e.printStackTrace();
        }
    }
}