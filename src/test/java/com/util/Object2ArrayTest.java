package com.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.util.Object2Array;

public class Object2ArrayTest {
	
	@Test
	public void ObjectToByteTest() {
		Map map = new HashMap();
		map.put("1", "a");
		byte[] b = Object2Array.ObjectToByte(map);
		System.out.println(b);
	}
	
	@Test
	public void ByteToObjectTest() {
		Map map = new HashMap();
		map.put("1", "a");
		byte[] b = Object2Array.ObjectToByte(map);
		System.out.println(Object2Array.ByteToObject(b));
		
	}
}
