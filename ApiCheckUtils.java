package com.wonders.util;

import java.util.*;

/**
 * @author lorelei
 * @date 2019/11/18 11:48
 * api接口校验
 */
public class ApiCheckUtils {
	//签名校验
	public static int check_request(Map<String, Object> params){
		Long timestamp = Long.valueOf(params.get("timestamp").toString()); //时间戳
		String receive_check_code = params.get("checkCode").toString(); //校验码
		long current_time = System.currentTimeMillis();
		try {
			//请求超时
			if(timestamp<(current_time-5*60*1000)){
				return 101;
			}
			String verify_check_code= MD5Utils.encrypt(params.get("checkAppend").toString(),"utf-8");
			//校验通过
			if(verify_check_code.equals(receive_check_code)){
				return 200;
			}
		} catch (Exception e){
			e.printStackTrace();
			return 105;
		}
		return 102;
	}

	//Map排序(按照首字母排序)
	public static String getSign(Map<String,Object> map){
		ArrayList<String> list = new ArrayList<String>();
		for(Map.Entry<String,Object> entry:map.entrySet()){
			if(entry.getValue()!=""&&entry.getKey()!="checkCode"){
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String [] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < size; i ++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		return result;
	}

	public static void main(String[] args) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ba",new Object());
		params.put("aa",new Object());
		params.put("ca",new Object());
		System.out.println(getSign(params));
	}
}
