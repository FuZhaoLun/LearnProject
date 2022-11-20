package com.example.kafka.util;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * kafka有用户名验证的配置
 */
@Component
public class KafkaUtil {
    //服务器地址 没密码使用PLAINTEXT前缀 有密码了使用SASL_PLAINTEXT前缀
    //public static final String servers="SASL_PLAINTEXT://10.172.16.27:1024,10.172.16.28:1024,10.172.16.29:1024";
    //要使用set方式读取配置文件里面内容将数据放到静态变量中
    private static String bootstrapServers;

    @Value("${spring.kafka.bootstrap-servers}")
    public void setBootstrapServers(String bootstrapServers) {
        KafkaUtil.bootstrapServers = bootstrapServers;
    }

    private static Integer retries;

    @Value("${spring.kafka.producer.retries}")
    public void setRetries(Integer retries) {
        KafkaUtil.retries = retries;
    }

    private static Integer batchSize;

    @Value("${spring.kafka.producer.batch-size}")
    public void setBatchSize(Integer batchSize) {
        KafkaUtil.batchSize = batchSize;
    }

    private static String acks;

    @Value("${spring.kafka.producer.acks}")
    public void setAcks(String acks) {
        KafkaUtil.acks = acks;
    }

    //kafka集群生产者配置
    public KafkaProducer<String, String> getProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.setProperty("security.protocol", "PLAINTEXT");
        props.setProperty("sasl.mechanism", "SCRAM-SHA-512");
        return new KafkaProducer<String, String>(props);
    }

    public KafkaConsumer<String, String> getConsumer(String groupId) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "PLAINTEXT://" + bootstrapServers);
        props.put("auto.offset.reset", "earliest"); //必须要加，如果要读旧数据
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "100");
        props.put("max.partition.fetch.bytes", "10240");//每次拉取的消息字节数，10K？，每次取回20条左右
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("security.protocol", "PLAINTEXT");
        props.setProperty("sasl.mechanism", "SCRAM-SHA-512");
        return new KafkaConsumer<String, String>(props);
    }
}