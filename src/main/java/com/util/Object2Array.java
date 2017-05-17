package com.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Object2Array {
	public static byte[] ObjectToByte(Object obj) {
		byte[] bytes = null;
		ObjectOutputStream oo = null;
		ByteArrayOutputStream bo = null;  
        try{
        	bo = new ByteArrayOutputStream();
        	oo = new ObjectOutputStream(bo);
        	oo.writeObject(obj);
        	oo.flush();
        	bytes = bo.toByteArray();
        }catch (Exception e) {
        	e.printStackTrace();
        }finally {
        	try {
				oo.close();
				bo.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
        }
        return bytes;
	}
	
	public static Object ByteToObject(byte[] bytes) {
		Object obj = null;
		ByteArrayInputStream bi = null;
		ObjectInputStream oi = null;
		try {
			bi = new ByteArrayInputStream(bytes);
			oi = new ObjectInputStream(bi);
			obj = oi.readObject();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				bi.close();
				oi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}
