package vn.com.huylq.kafkaautocommit.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AutoCommitConsumer {

    @KafkaListener(topics = "bank-transactions", groupId = "auto-group",
            containerFactory = "autoKafkaListenerContainerFactory")
    public void consume(String message) {
        log.info("[AUTO] Received transaction: {}", message);
        // Simulate processing
        processTransaction(message);
        // Simulate crash
        if (message.contains("CRASH")) {
            throw new RuntimeException("[AUTO] Simulated crash after processing");
        }
    }

    private void processTransaction(String msg) {
        log.info("[AUTO] Processing transaction: {}", msg);
        // e.g., update balance in DB
        try {
            Thread.sleep(10000); // Simulate processing time
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
