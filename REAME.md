# Kafka Auto-Commit Demo: Why Manual Commit Matters

This demo shows how **blindly relying on Kafka auto-commit** can lead to **data loss or inconsistency**, especially in **financial transaction systems**.

---

## 📘 Overview

Kafka allows consumers to track progress using **offsets**.  
Offsets must only be committed **after** successful processing of a message.

### ❗Auto-Commit Danger

If the consumer **processes a transaction but crashes before auto-commit runs**, Kafka believes it was processed → it **won’t be redelivered** → 🧨 **Transaction loss**.

---

## 🛠️ Project Structure

```
kafka-auto-commit-demo/
├── docker-compose.yml
├── README.md
├── src/main/java/
│ └── com/example/kafka/
│ ├── AutoCommitConsumer.java
│ ├── ManualCommitConsumer.java
│ ├── KafkaTransactionProducer.java
│ └── KafkaConfig.java
└── pom.xml
```

---

## 🚀 Getting Started

### 1. Start Kafka + Zookeeper

```bash
docker-compose up -d
```

Check logs:
```bash
docker logs -f kafka
```

Create the topic:
```bash
docker exec -it kafka kafka-topics.sh --create --topic bank-transactions --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

### 2. Run the Demo

Run using your IDE or:
```bash
./mvnw spring-boot:run
```