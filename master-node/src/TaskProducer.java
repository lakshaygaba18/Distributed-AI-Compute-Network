import java.io.PrintWriter;
import java.net.Socket;

public class TaskProducer {

    public static void addTasks() {

        String[] tasks = {
                "SUM 1 100",
                "SUM 1 10",
                "SUM 1 5",
                "SUM 1 20",
                "SUM 1 50"
        };

        try {

            for (String task : tasks) {

                Socket socket =
                        new Socket("localhost", 8000);

                PrintWriter writer =
                        new PrintWriter(
                                socket.getOutputStream(),
                                true);

                writer.println("ADD " + task);

                writer.close();
                socket.close();
            }

            System.out.println(
                    "Producer Added "
                            + tasks.length
                            + " Tasks To Queue Server");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}