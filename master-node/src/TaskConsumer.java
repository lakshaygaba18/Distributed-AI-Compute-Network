import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TaskConsumer {

    public static String getNextTask() {

        try {

            Socket socket =
                    new Socket("localhost", 8000);

            PrintWriter writer =
                    new PrintWriter(
                            socket.getOutputStream(),
                            true);

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()));

            writer.println("GET");

            String task =
                    reader.readLine();

            reader.close();
            writer.close();
            socket.close();

            return task;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}