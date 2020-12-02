package com.example.publisher;

import com.example.dto.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RequiredArgsConstructor
public class PublisherApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PublisherApplication.class, args);
    }

    @Value("${active-mq.topic}")
    private String topic;

    private final JmsTemplate jmsTemplate;

    @Override
    public void run(String... args) throws Exception {
        AtomicInteger counter = new AtomicInteger();
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(
                () -> jmsTemplate.convertAndSend(topic, new Employee(counter.incrementAndGet(), "Ivan")),
                0,
                1,
                TimeUnit.SECONDS);
        Thread.sleep(15 * 100 * 1000);
        scheduledExecutorService.shutdown();
    }
}
