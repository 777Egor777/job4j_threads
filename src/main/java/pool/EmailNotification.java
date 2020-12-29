package pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 29.12.2020
 */
public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        pool.submit(
                () -> send(getSubject(user), getBody(user), user.getEmail())
        );
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getSubject(User user) {
        return String.format("Notification %s to email %s.",
                user.getName(), user.getEmail());
    }

    public String getBody(User user) {
        return String.format("Add a new event to %s",
                user.getName());
    }

    private void send(String subject, String body, String email) {
    }
}
