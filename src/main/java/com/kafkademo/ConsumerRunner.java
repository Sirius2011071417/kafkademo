package com.kafkademo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

public class ConsumerRunner implements Runnable {
	private final AtomicBoolean closed = new AtomicBoolean(false);
	private final KafkaConsumer<Long, String> consumer;
	private final TopicPartition tp;
	private String topic;
	private Properties p;
	
	public ConsumerRunner(Properties p) {
		this.p = p;
		Properties props = new Properties();
       	props.put("bootstrap.servers", p.getProperty("kafka_host"));
      	props.put("group.id", p.getProperty("group"));
        props.put("enable.auto.commit", "true");
      	props.put("auto.commit.interval.ms", "1000");
       	props.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
       	props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
       	props.put("auto.offset.reset", "earliest");
        consumer = new KafkaConsumer<Long, String>(props);
        tp = new TopicPartition(p.getProperty("topic"), 0);
        this.topic = this.p.getProperty("topic");
        consumer.subscribe(Arrays.asList(p.getProperty("topic")));
        System.out.printf("consumer begin......server: %s, topic: %s, key.serializer: %s, value.serializer: %s%n", p.getProperty("kafka_host"), this.topic, "Long", "String");
//        consumer.assign(Arrays.asList(tp));
//        consumer.seekToBeginning(Arrays.asList(tp));
	}
	
	public void run() {
		while(!closed.get()) {		
			ConsumerRecords<Long, String> records = consumer.poll(100);
			 for (ConsumerRecord<Long, String> record : records) {
                 System.out.printf("offset = %d, partition = %s,key = %s, value = %s%n", record.offset(), record.partition(), record.key(), record.value().substring(0, Integer.parseInt(this.p.getProperty("value_limit", "1"))));
             }
		}
	}

	public void shutdown() {
		closed.set(true);
		consumer.wakeup();
	}
}
