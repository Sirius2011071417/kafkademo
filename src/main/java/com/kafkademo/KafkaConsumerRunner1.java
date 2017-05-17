package com.kafkademo;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.json.JSONObject;


public class KafkaConsumerRunner1 implements Runnable {
	private final AtomicBoolean closed = new AtomicBoolean(false);
	private final KafkaConsumer<String, String> consumer;
	private final TopicPartition tp;
	
	public KafkaConsumerRunner1() {
		Properties props = new Properties();
       	props.put("bootstrap.servers", "localhost:9092");
      	props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
      	props.put("auto.commit.interval.ms", "1000");
       	props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
       	props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<String, String>(props);
        tp = new TopicPartition("topic1", 0);
//        consumer.subscribe(Arrays.asList("topic1"));
        consumer.assign(Arrays.asList(tp));
//        consumer.seekToBeginning(Arrays.asList(tp));
	}
	
	public void run() {
		while(!closed.get()) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			 for (ConsumerRecord<String, String> record : records) {
				 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				 JSONObject jsonobj = new JSONObject(record.value()); 
//               System.out.println("json---" + jsonobj.get("update"));
				 System.out.printf("offset = %d, partition = %s,key = %s, value = %s%n", record.offset(), record.partition(), record.key(), record.value());
             }
		}
	}

	public void shutdown() {
		closed.set(true);
		consumer.wakeup();
	}
}
