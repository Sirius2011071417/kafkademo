package com.kafkademo;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

/*
 * 1.用sax解析XML
 * 2.kafka producer data(String)
 */
public class Producer
{
	public static void main(String[] args) {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream(new File(args[0])));
	        SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser parser = factory.newSAXParser();
	        File file = new File(props.getProperty("xml_file"));
	        DefaultHandler pageHandler = new XMLProducer(props);
	        parser.parse(file, pageHandler);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
