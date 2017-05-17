package com.kafkademo;

public class KafkaConsumerProducerDemo {

	public static void main(String[] args) {
		Thread t = new Thread(new KafkaProducerRunner());
//		Thread tt = new Thread(new KafkaConsumerRunner());
		Thread t1 = new Thread(new KafkaConsumerRunner1());
		t.start();
//		tt.start();
		t1.start();
	}
}
