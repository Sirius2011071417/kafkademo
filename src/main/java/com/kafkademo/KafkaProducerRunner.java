package com.kafkademo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;

public class KafkaProducerRunner implements Runnable {
	 
	 private final KafkaProducer<String, String> producer;
	 private List<String> list = new ArrayList<String>();
	 
	 public KafkaProducerRunner() {
		 Properties props = new Properties();
		 props.put("bootstrap.servers", "localhost:9092");
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 producer = new KafkaProducer<String, String>(props);
		 
		 list.add("{'_id': '1111'}");
		 list.add("{'info_dtl_area': null, 'info_uuid': '1111', 'categories': null, 'related_orgs': [{'org_name': '\u6c5f\u9634\u66a8\u5357\u5efa\u8bbe\u5f00\u53d1\u6709\u9650\u516c\u53f8', 'org_uuid': '60979fb6-ec66-64da-8c93-06091d0af1e6', 'org_role': 'ER0001'}, {'org_name': '\u6c5f\u9634\u65b9\u6b63\u5efa\u8bbe\u5de5\u7a0b\u9020\u4ef7\u4e8b\u52a1\u6240\u6709\u9650\u516c\u53f8', 'org_uuid': '31fdd30c-13b5-cbdc-ccad-3d15b95306f0', 'org_role': 'ER0003'}], 'brand_tags': null, 'jc_tags': null, 'pro_pk': 24152412, 'info_pk': 25081653, 'info_dtl_length': null, 'info_dtl_quantity': null, 'custom_tags': ['GC0117', 'GC0201', 'GC0301', 'GC0499Z', 'GC0499']}");
	 }
	
	 public void run() {
//		 for(int i = 0; i < 100; i++){
		 for(int i=0;i<list.size();i++){
		     producer.send(new ProducerRecord<String, String>("topic1", "update", list.get(i)));
//		     producer.send(new ProducerRecord<String, String>("my-topic", Integer.toString(i), list.get(i)));
//		     System.out.println(i);
		 }
		 producer.close();
	}
}
