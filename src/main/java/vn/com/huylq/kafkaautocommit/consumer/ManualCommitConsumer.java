package vn.com.huylq.kafkaautocommit.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ManualCommitConsumer {

    @KafkaListener(topics = "bank-transactions", groupId = "manual-group",
            containerFactory = "manualKafkaListenerContainerFactory")
    public void consume(String message, Acknowledgment ack) {
        try {
            log.info("[MANUAL] Received transaction: {}", message);
            processTransaction(message);
            ack.acknowledge(); // commit only after success
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
            // Don't ack → will be retried
        }
    }

    private void processTransaction(String msg) {
        log.info("[MANUAL] Processing transaction: {}", msg);
        if (msg.contains("CRASH")) {
            throw new RuntimeException("[MANUAL] Simulated crash after processing");
        }
    }
}
