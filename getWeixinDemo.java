import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class getWeixinDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String Url = "https://mp.weixin.qq.com/mp/newappmsgvote?action=show&__biz=MzA5NTQ3ODAxMQ==&supervoteid=495238429&uin=&key=&pass_ticket=&wxtoken=777&mid=2651701902&idx=2&appmsg_token=";
		JSONArray inJson = null;
		Iterator ite = null;
		String result = readContentFromGet(Url);
		String rgex = new String();
        rgex = "var voteInfo = (.*?);";
        
        JSONObject jsonObject = JSONObject.parseObject(getSubUtilSimple(result, rgex));
        String voteSubject = jsonObject.getString("vote_subject");
        JSONObject vote_subject = JSONObject.parseObject(voteSubject.substring(1, voteSubject.length()-1));
        inJson = vote_subject.getJSONArray("options");
        
    //    System.out.println(vote_subject.toJSONString());
		for(ite = inJson.iterator();ite.hasNext();){
			JSONObject job = (JSONObject)ite.next();
			System.out.println(job.get("name").toString().substring(3)+":"+job.get("cnt"));
		}
		
	}
	
	/**读取文本转成字符串
	 * @param fileName 路径
	 * @return
	 */
	public static String readToString(String fileName) {  
        //String encoding = "UTF-8";  
        File file = new File(fileName);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return new String(filecontent);  
    }
	/** 
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样 
     * @param soap 
     * @param rgex 
     * @return 
     */
	public static String getSubUtilSimple(String soap,String rgex){  
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式  
        Matcher m = pattern.matcher(soap);  
        while(m.find()){  
            return m.group(1);  
        }  
        return "";  
    } 
	
	/** 
     * 正则表达式匹配两个指定字符串中间的内容 
     * @param soap 文本
     * @param rgex 规则
     * @return 
     */  
    public static List<String> getSubUtil(String soap,String rgex){  
        List<String> list = new ArrayList<String>();  
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式  
        Matcher m = pattern.matcher(soap);  
        while (m.find()) {  
            int i = 1;  
            list.add(m.group(i));  
            i++;  
        }  
        return list;  
    }
    
    public static String readContentFromGet(String UrlStr) {
        
    	try{
    		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
            URL getUrl = new URL(UrlStr);
            // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
            // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到服务器
            connection.connect();
            // 取得输入流，并使用Reader读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
//            System.out.println("=============================");
//            System.out.println("Contents of get request");
//            System.out.println("=============================");
            String lines = "";
            while ((reader.readLine()) != null) {
                // lines = new String(lines.getBytes(), "utf-8");
                lines = lines + reader.readLine();
            }
            reader.close();
            // 断开连接
            connection.disconnect();
//            System.out.println("=============================");
//            System.out.println("Contents of get request ends");
//            System.out.println("=============================");
            return lines;
    	}catch(Exception e){
			e.printStackTrace();
		}
    	return null;
        
    }

}
