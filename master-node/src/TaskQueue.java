import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {

    private static final Queue<String> queue =
            new LinkedList<>();

    public static synchronized void addTask(
            String task) {

        queue.add(task);

        System.out.println(
                "Task Added: " + task);
    }

    public static synchronized String getTask() {

        return queue.poll();
    }

    public static synchronized int size() {

        return queue.size();
    }
    public static synchronized void printQueue() {

        System.out.println(queue);
    }
}