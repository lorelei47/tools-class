import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class modianUtils {
	/*
	 * test
	 */
	public static void main(String[] args) throws MalformedURLException {
			Iterator ite = null;
			String proId = "36610";
			String baseUrl1 = "https://wds.modian.com/api/project/orders";
			String baseUrl2 = "https://wds.modian.com/api/project/sorted_orders";
			String baseUrl3 = "https://wds.modian.com/api/project/rankings";
			String baseUrl4 = "https://wds.modian.com/api/project/detail";
			
			HashMap<String, String> parameters = new HashMap<String, String>();
			parameters.put("pro_id", proId);
			parameters.put("page", "0");
			JSONArray result = sendPost(baseUrl1, parameters);
			for(ite = result.iterator();ite.hasNext();){
				JSONObject job = (JSONObject)ite.next();
				System.out.println(job.get("nickname")+":"+job.get("backer_money"));
			}
		
	}
	/*
	 * 发送post请求
	 * @result 返回json (默认每页只取20条数据)
	 */
	public static JSONArray sendPost(String baseUrl, HashMap<String, String> parameters) {
		JSONArray inJson = null;
		SignUtils signUtils = new SignUtils();

		try {
			URL url = new URL(baseUrl);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent",
					"User-Agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/2008052906 Firefox/3.0");
			connection.setReadTimeout(8000);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			PrintWriter out = new PrintWriter(connection.getOutputStream());

			String param = "";

			if(parameters.entrySet() == null || parameters.entrySet().iterator() == null){
				return null;
			}
			
			Iterator<Entry<String, String>> iter = parameters.entrySet().iterator();
			String key;
			String val;
			while (iter.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>) iter.next();
				key = entry.getKey();
				val = entry.getValue();
				param = param + key + "=" + val + "&";
			}

			param = param + "sign=" + signUtils.getSign(baseUrl, parameters);
			
			out.print("");
			out.print(param);
			out.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

			JSONObject result = JSONObject.parseObject(in.readLine());
			
			inJson = result.getJSONArray("data");

		} catch (Exception e) {
			System.out.println("摩点：" + baseUrl + " 连接异常");
		//	log.error("URL：" + baseUrl + "..." + "param：" + parameters + "..." + e.getMessage());
		}

		return inJson;
	}
}
