package com.example.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Data;

/**
 * 3DES加解密
 * @author SHANHY(365384722@QQ.COM)
 * @date 2015-8-18
 */
public class ThreeDES {

	// 定义 加密算法,可用DES,DESede,Blowfish
	private static final String Algorithm = "DESede";
	private static final byte[] key = build3DesKey("123456");

	/**
	 * 加密方法
	 * 
	 * @param keybyte
	 *            加密密钥，长度为24字节
	 * @param src
	 *            被加密的数据缓冲区（源）
	 * @return
	 * @author SHANHY
	 * @date 2015-8-18
	 */
	public static byte[] encryptMode( byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(key, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param keybyte
	 *            加密密钥，长度为24字节
	 * @param src
	 *            加密后的缓冲区
	 * @return
	 * @author SHANHY
	 * @date 2015-8-18
	 */
	public static byte[] decryptMode( byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(key, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * 转换成十六进制字符串
	 * 
	 * @param b
	 * @return
	 * @author SHANHY
	 * @date 2015-8-18
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}
	
	/**
	 * 字符串转16进制字符串.
	 * @param str
	 * @return
	 */
	public static String str2HexStr(String str) {  
		char[] chars = "0123456789ABCDEF".toCharArray();  
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();  
		int bit;  
		for (int i = 0; i < bs.length; i++) {  
			bit = (bs[i] & 0x0f0) >> 4;  
		sb.append(chars[bit]);  
		bit = bs[i] & 0x0f;  
		sb.append(chars[bit]);  
		}  
		return sb.toString();  
	} 
	
	/**
	 * byte数组转16进制字符串.
	 * @param bArray
	 * @return
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}
	
	/**
	 * 16进制字符串转byte数组.
	 * @param hex
	 * @return
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}
	
	private static int toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * 根据字符串生成密钥字节数组.
	 * @param keyStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] build3DesKey(String keyStr) {
		try{
			byte[] key = new byte[24];    //声明一个24位的字节数组，默认里面都是0
			byte[] temp = keyStr.getBytes("UTF-8");    //将字符串转成字节数组
			//执行数组拷贝
			//System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
			if(key.length > temp.length){
				//如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
				System.arraycopy(temp, 0, key, 0, temp.length);
			}else{
				//如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
				System.arraycopy(temp, 0, key, 0, key.length);
			}
			return key;
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
			throw new RuntimeException("获取加密字符串异常");
		}
	}
	
	public static String decrypt(String keyStr) {
		byte[] decryptByte = decryptMode(hexStringToByte(keyStr));
		System.out.println("解密后："+new String(decryptByte));
		return new String(decryptByte);
	}
	public static String encrypt(String keyStr) {
		byte[] encrybyte = encryptMode(keyStr.getBytes());
		String encryStr = bytesToHexString(encrybyte);
		System.out.println("密文(16进制字符串)："+encryStr);
		return encryStr;
	}
	
	public static void main(String[] args) {
		Date a1 = new Date();
		String str = "u=jsmis&pw=123456&date="+a1.getTime();
		
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("uid", "order");
		a.put("pw", "123456");
		a.put("date", a1.getTime());
		
		System.out.println(JSON.toJSON(a).toString());// 
//		JSON.toJSON(dormitoryList).toString()
//		json
		str = JSON.toJSON(a).toString();
		System.out.println("明文："+str);
		
		byte[] encrybyte = encryptMode(str.getBytes());
		String encryStr = bytesToHexString(encrybyte);
//		String aa ="C65A5206A3D9393D8FC77AB53EDA93DF5CB048BF74864CDA3957279F1D1D3329E943E2E122595E9A";
		System.out.println("密文(16进制字符串)："+encryStr);
		
		byte[] decryptByte = decryptMode(encrybyte);
		System.out.println("解密后："+new String(decryptByte));
		JSONObject jsonObject = JSONObject.parseObject(new String(decryptByte));  
		Map<String, Object> ac = new HashMap<String, Object>();
		ac.put("uid", jsonObject.get("uid"));
		ac.put("pw", jsonObject.get("pw"));
		ac.put("date", jsonObject.get("date"));
		System.out.println(ac);
	}
	
	

}