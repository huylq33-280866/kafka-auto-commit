package vn.com.huylq.kafkaautocommit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaAutoCommitApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaAutoCommitApplication.class, args);
    }
}
