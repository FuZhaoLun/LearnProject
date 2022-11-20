package com.example.kafka.service;

import com.alibaba.fastjson.JSONObject;
import com.example.kafka.util.KafkaUtil;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

/**
 * 生产者
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ProducerClient {
    @Autowired
    KafkaUtil kafkaUtil;

    @Test
    public void test() throws Exception {
        String value = """
                {
                    "name":"fuzhaolun",
                    "age":"25",
                    "sex":"man",
                    "birthday":"1997-04-04"
                }""";
        sendToKafka("test", "key3", JSONObject.parseObject(value));
    }

    public void sendToKafka(String topic, String processId, JSONObject bpmData) throws Exception {
        Producer<String, String> producer = this.kafkaUtil.getProducer();
        try {
            final ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic,
                    processId, bpmData.toJSONString());
            Future<RecordMetadata> send = producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("sendToKafka-发送至Kafka:" + "d+key-" + processId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.close();
    }
}