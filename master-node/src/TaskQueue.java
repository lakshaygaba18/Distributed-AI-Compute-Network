import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {
    private static final Queue<String> processingQueue =
            new LinkedList<>();

    private static final Queue<String> queue =
            new LinkedList<>();

    public static synchronized void addTask(
            String task) {

        queue.add(task);

        System.out.println(
                "Task Added: " + task);
    }

    public static synchronized String getTask() {

        String task = queue.poll();

        if (task != null) {

            processingQueue.add(task);
        }

        return task;
    }
    public static synchronized void acknowledgeTask(
            String task) {

        processingQueue.remove(task);

        System.out.println(
                "Removed From Processing Queue: "
                        + task);
    }
    public static synchronized int size() {

        return queue.size();
    }
    public static synchronized void printQueue() {

        System.out.println(queue);
    }
}