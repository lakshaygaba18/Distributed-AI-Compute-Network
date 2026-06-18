import java.io.*;
import java.net.Socket;

public class TestRegistryClient {

    public static void main(String[] args) {

        try {

            Socket socket =
                    new Socket("localhost", 9000);

            PrintWriter writer =
                    new PrintWriter(
                            socket.getOutputStream(),
                            true);

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()));

            writer.println("GET_WORKERS");

            String response =
                    reader.readLine();

            System.out.println(response);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}