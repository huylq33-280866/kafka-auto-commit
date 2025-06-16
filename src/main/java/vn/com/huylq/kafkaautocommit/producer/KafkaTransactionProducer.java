package vn.com.huylq.kafkaautocommit.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaTransactionProducer implements CommandLineRunner {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void run(String... args) {
        send("Alice", "Bob", 100);
        send("Carol", "Dave", 200);
        send("Eve", "Mallory", 300, true); // Simulate crash
    }

    private void send(String from, String to, int amount) {
        send(from, to, amount, false);
    }

    private void send(String from, String to, int amount, boolean crash) {
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("from", from);
        transaction.put("to", to);
        transaction.put("amount", amount);
        if (crash) transaction.put("note", "CRASH");

        try {
            kafkaTemplate.send("bank-transactions", new ObjectMapper().writeValueAsString(transaction));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("Sent transaction: {}", transaction);
    }
}
