package com.example.kafka.service;

import com.example.kafka.util.KafkaUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 消费者
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ConsumerClient {
    @Autowired
    KafkaUtil kafkaUtil;
    public static KafkaConsumer<String, String> consumer = null;

    @Test
    public void test() throws Exception {
        fecthKafka();
    }

    public void fecthKafka() throws Exception {
        consumer = this.kafkaUtil.getConsumer("console-consumer-3942"); //group
        consumer.subscribe(List.of("test"));//topic
        int i = 0;
        while (true) {
            ConsumerRecords<String, String> records;
            try {
                records = consumer.poll(Long.MAX_VALUE);//毫秒
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("fetched from partition " + record.partition() + ", offset: " + record.offset() + ",key: " + record.key() + ",value:" + record.value());
                i++;
                System.out.println(i);
            }
            try {
                consumer.commitSync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}