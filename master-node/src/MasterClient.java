import java.io.*;
import java.net.Socket;

public class MasterClient {

    public static void main(String[] args) {

        try {

            // Registry se workers fetch karo

            Socket registrySocket =
                    new Socket("localhost", 9000);

            PrintWriter registryWriter =
                    new PrintWriter(
                            registrySocket.getOutputStream(),
                            true);

            BufferedReader registryReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    registrySocket.getInputStream()));

            registryWriter.println("GET_WORKERS");

            String response =
                    registryReader.readLine();

            registryReader.close();
            registryWriter.close();
            registrySocket.close();

            System.out.println("Workers Found: " + response);

            if (response == null || response.equals("[]")) {
                System.out.println("No workers available.");
                return;
            }

            response = response.replace("[", "");
            response = response.replace("]", "");

            String[] workerPorts =
                    response.split(",");

            String[] tasks = {
                    "SUM 1 100",
                    "SUM 1 10",
                    "SUM 1 5",
                    "SUM 1 20",
                    "SUM 1 50"
            };

            int workerIndex = 0;

            for (String task : tasks) {

                boolean completed = false;
                int attempts = 0;

                while (!completed &&
                        attempts < workerPorts.length) {

                    int port =
                            Integer.parseInt(
                                    workerPorts[workerIndex].trim());

                    try {

                        Socket socket =
                                new Socket("localhost", port);

                        PrintWriter writer =
                                new PrintWriter(
                                        socket.getOutputStream(),
                                        true);

                        BufferedReader reader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                socket.getInputStream()));

                        writer.println(task);

                        String result =
                                reader.readLine();

                        System.out.println(
                                "Task: " + task +
                                        " | Worker: " + port +
                                        " | Result: " + result
                        );

                        reader.close();
                        writer.close();
                        socket.close();

                        completed = true;

                    } catch (Exception e) {

                        System.out.println(
                                "Worker " + port +
                                        " failed. Trying next worker...");

                        workerIndex++;

                        if (workerIndex ==
                                workerPorts.length) {
                            workerIndex = 0;
                        }

                        attempts++;
                    }
                }

                if (!completed) {
                    System.out.println(
                            "Task failed: " + task);
                }

                workerIndex++;

                if (workerIndex ==
                        workerPorts.length) {
                    workerIndex = 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}