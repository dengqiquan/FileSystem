package com.fcore.base.fileSystem.utils;

import java.util.HashMap;
import java.util.Map;

public class MapResult {
	
	static public interface Invoker{
		public void writeBody(Map<String, Object> body) throws Exception;
	}
	
	public static Map<String, Object> invoke(Invoker invoker){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> head = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		try{
			head.put("resultCode", "200");
			head.put("message", "ok");
			invoker.writeBody(body);
		}catch(Exception e){
			head.put("resultCode", "-1");
			head.put("message", "exception");
			e.printStackTrace();
			
		}
		resultMap.put("head", head);
		resultMap.put("body", body);
		return resultMap;
	}

}
