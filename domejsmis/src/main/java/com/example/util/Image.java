package com.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Image {

	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imgFilePath  路径及图片名称
	 * @return
	 */
	public static String GetImageStr(String imgFilePath) {
		byte[] data = null;
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}
	
	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imgFilePath  路径及图片名称
	 * @return
	 */
	public static byte[] GetImageStrbyte(String imgFilePath) {
		byte[] data = null;
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return data;
	}

	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * @param imgStr  Base64解码
	 * @param imgFilePath   输出路径及图片名称
	 * @return 
	 */
	public static boolean GenerateImage(String imgStr, String imgFilePath) {
		if (imgStr == null) 
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * @param imgStr  Base64解码
	 * @param imgFilePath   输出路径及图片名称
	 * @return 
	 */
	public static boolean GenerateImage(byte[] bytes,String imgFilePath) {
		try {
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			System.out.println(imgFilePath);
			createFile(imgFilePath);
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public static boolean createFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return false;
		}
		if (filePath.endsWith(File.separator)) {
			return false;
		}
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				return false;
			}
		}
		return true;
	}
}
