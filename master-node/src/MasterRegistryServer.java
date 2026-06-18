import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class MasterRegistryServer {

    private static final Map<Integer, Long> workers =
            new HashMap<>();
    public static List<Integer> getWorkers() {
        return new ArrayList<>(workers.keySet());
    }

    public static void main(String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(9000);

            new Thread(() -> {

                while (true) {

                    try {

                        long currentTime =
                                System.currentTimeMillis();

                        List<Integer> deadWorkers =
                                new ArrayList<>();

                        for (Integer port : workers.keySet()) {

                            long lastHeartbeat =
                                    workers.get(port);

                            if (currentTime - lastHeartbeat > 10000) {

                                deadWorkers.add(port);
                            }
                        }

                        for (Integer port : deadWorkers) {

                            workers.remove(port);

                            System.out.println(
                                    "Worker " + port +
                                            " marked dead");
                        }

                        Thread.sleep(5000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }).start();

            while (true) {

                Socket socket = serverSocket.accept();

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));

                String message = reader.readLine();

                if (message.startsWith("REGISTER")) {

                    int port =
                            Integer.parseInt(
                                    message.split(" ")[1]);

                    workers.put(
                            port,
                            System.currentTimeMillis());

                    System.out.println(
                            "Worker Registered: " + port);

                    System.out.println(
                            "Current Workers: " + workers);
                }
                else if (message.equals("GET_WORKERS")) {

                    PrintWriter writer =
                            new PrintWriter(
                                    socket.getOutputStream(),
                                    true);

                    writer.println(workers.keySet().toString());
                }
                else if(message.startsWith("HEARTBEAT"))
                {
                    int port =
                            Integer.parseInt(
                                    message.split(" ")[1]);

                    workers.put(
                            port,
                            System.currentTimeMillis());

                    System.out.println(
                            "Heartbeat from " + port);
                }

                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}