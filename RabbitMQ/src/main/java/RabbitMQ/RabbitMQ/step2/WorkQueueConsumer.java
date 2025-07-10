package RabbitMQ.RabbitMQ.step2;

import org.springframework.stereotype.Component;

@Component
public class WorkQueueConsumer {
    public void workQueueTask(String message){
        String[] messageParts = message.split("\\|");
        String originMessage = messageParts[0];
        int duration = Integer.parseInt(messageParts[1].trim());

        System.out.println("Received: " + originMessage + "(duration: " + duration + "ms)");

        try {
            int second = duration /1000;
            for (int num = 0; num < second; num++) {
                Thread.sleep(1000);
                System.out.print(".");
            }
            Thread.sleep(duration);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        System.out.println("\nCompleted: " + originMessage);
    }
}
