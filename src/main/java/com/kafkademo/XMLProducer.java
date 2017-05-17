package com.kafkademo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLProducer extends DefaultHandler {
	
	private final KafkaProducer<Long, String> producer;
	private Map<String, String> map;
	private List<Map> list;
	private String table_str;
	private String table_col;
	private String tagName;
	private StringBuilder sb = new StringBuilder(); 
	private long index;
	private Properties p;
	private String topic;

	public XMLProducer(Properties p) {
		 this.p = p;
		 Properties props = new Properties();
		 props.put("bootstrap.servers", p.getProperty("kafka_host"));
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 this.producer = new KafkaProducer<Long, String>(props);
		 this.topic = this.p.getProperty("topic");
		 System.out.printf("producer begin......server: %s, topic: %s, key.serializer: %s, value.serializer: %s%n", p.getProperty("kafka_host"), this.topic, "Long", "String");
	 }
	
	@Override
    public void startDocument() throws SAXException {
		list = new ArrayList<Map>();
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
    	this.tagName = qName;
    	sb.setLength(0);
    	if(qName.equals("table_data")) {
    		this.map = new HashMap<String, String>();
    		this.map.put("table_name", attributes.getValue("name"));
    		this.table_str = qName;
    	}
    	if(qName.equals("row")) {
    		this.table_col = qName;
    	}
    	if(this.table_col != null && qName.equals("field")) {
    		this.table_col = attributes.getValue("name");
    	}
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
    	if(qName.equals("table_data")) {
    		this.table_str = null;
    	}
    	if(qName.equals("row")) {
    		this.table_col = null;   
    		this.producer.send(new ProducerRecord<Long, String>(this.topic, index++, this.map.toString()));
//    		if(index % 1000 == 0) {
//    			System.out.println("生产了" + (index -1) + "个......");
//    		}
    		System.out.println("producer: key: " + (index-1) + " value: " + this.map.toString().substring(0, Integer.parseInt(this.p.getProperty("value_limit", "1"))));
    	}
    	if(this.table_col != null && qName.equals("field")) {
    		String value = this.sb.toString();
    		this.map.put(this.table_col, value);
    	}
    }

    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
    	if(this.tagName.equals("field") && this.table_col != null) {
    		String value = new String(ch, start, length);
    		sb.append(value);
    	}
    }
}
