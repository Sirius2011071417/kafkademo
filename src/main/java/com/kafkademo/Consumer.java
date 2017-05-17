package com.kafkademo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/*
 * kafka consumer data(String) from XML
 */
public class Consumer {

	public static void main(String[] args) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(new File(args[0])));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		new Thread(new ConsumerRunner(props)).start();
	}

}
