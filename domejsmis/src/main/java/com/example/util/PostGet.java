package com.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class PostGet {
	
	public static void main(String a[]){
		try {
			getResult("http://127.0.0.1:8080/restservice/web/approval/accpet","{name:111}",true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param url
	 * @param param  有参为post  json提交
	 * @param postType  true post,false get
	 * @return
	 * @throws Exception
	 */
	public static String getResult(String url,String param,Boolean postType) throws Exception {
	   	 String content = "";
			HttpURLConnection connection = null;
			try {
				URL restURL  = null;
				try {
					restURL  = new URL(url);
				} catch (Exception me) {
					me.printStackTrace();
				}
				if(restURL != null){
		 			connection = (HttpURLConnection) restURL.openConnection();
		 			connection.setConnectTimeout(1000*5);
		 			connection.setReadTimeout(5000);
		 			
		 			connection.setDoOutput(true);
		 			if(postType){
		 				connection.setRequestMethod("POST");
		 			}else{
		 				connection.setRequestMethod("GET"); 
		 			}
		 			connection.setRequestProperty("Content-Type", "application/json");
		 			if ((param != null) && (param.length() > 1)) {
		 				connection.setRequestMethod("POST");
		 				connection.setDoOutput(true);
		 				OutputStreamWriter outer = new OutputStreamWriter(
		 						connection.getOutputStream(), "UTF-8");
		 				outer.write(param);
		 				outer.flush();
		 				outer.close();
		 			}
		 			connection.connect();
		 			InputStream ips = connection.getInputStream();
		 			content = inputStreamToString(ips, "UTF-8");
		 			ips.close();
		 			connection.disconnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
			finally{
				if(connection != null){
					connection.disconnect();
				}
			}
			return content;
	    }

	
	public static String inputStreamToString(InputStream is, String charSet)
			throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is,
				charSet));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
			buffer.append("\r\n");
		}
		in.close();
		return buffer.toString();
	}
	
	/**
	 * 向指定URL发送POST请求
	 * 
	 * @param url
	 * @param paramMap 
     *            请求参数，请求参数应该是 key=value1&key2=value2 的形式。
	 * @return 响应结果
	 */
	public static String sendPost(String url, Map<String, Object> paramMap) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Charset", "UTF-8");

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());

			// 设置请求属性
			String param = "";
			if (paramMap != null && paramMap.size() > 0) {
				Iterator<String> ite = paramMap.keySet().iterator();
				while (ite.hasNext()) {
					String key = ite.next();// key
					String value = paramMap.get(key).toString();
					param += key + "=" + value + "&";
				}
				param = param.substring(0, param.length() - 1);
			}

			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.err.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("contentType", "utf-8");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}
