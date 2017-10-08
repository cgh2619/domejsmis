package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.util.ThreeDES;
@RestController
@SpringBootApplication
public class DemoApplication {

	@RequestMapping("/t")
	public Map<String, Object> hello(HttpServletRequest request){
		
		String token = request.getHeader("token");
		System.out.println(request.getParameter("token"));
		System.out.println(ThreeDES.decrypt(token));
		
		 JSONObject jsonObject = JSONObject.parseObject(ThreeDES.decrypt(token));  
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("uid", jsonObject.get("uid"));
		a.put("pw", jsonObject.get("pw"));
		a.put("date", jsonObject.get("date"));
		return jsonObject;
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
