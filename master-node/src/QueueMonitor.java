import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class QueueMonitor {

    public static void printStatus() {

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

            writer.println("SIZE");

            String size =
                    reader.readLine();

            System.out.println(
                    "Current Queue Size: "
                            + size);

            reader.close();
            writer.close();
            socket.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}