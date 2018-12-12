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
	
	/**��ȡ�ı�ת���ַ���
	 * @param fileName ·��
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
     * ���ص����ַ�������ƥ�䵽����Ļ��ͷ��ص�һ����������getSubUtilһ�� 
     * @param soap 
     * @param rgex 
     * @return 
     */
	public static String getSubUtilSimple(String soap,String rgex){  
        Pattern pattern = Pattern.compile(rgex);// ƥ���ģʽ  
        Matcher m = pattern.matcher(soap);  
        while(m.find()){  
            return m.group(1);  
        }  
        return "";  
    } 
	
	/** 
     * ������ʽƥ������ָ���ַ����м������ 
     * @param soap �ı�
     * @param rgex ����
     * @return 
     */  
    public static List<String> getSubUtil(String soap,String rgex){  
        List<String> list = new ArrayList<String>();  
        Pattern pattern = Pattern.compile(rgex);// ƥ���ģʽ  
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
    		// ƴ��get�����URL�ִ���ʹ��URLEncoder.encode������Ͳ��ɼ��ַ����б���
            URL getUrl = new URL(UrlStr);
            // ����ƴ�յ�URL�������ӣ�URL.openConnection���������URL�����ͣ�
            // ���ز�ͬ��URLConnection����Ķ�������URL��һ��http�����ʵ�ʷ��ص���HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            // �������ӣ�����ʵ����get requestҪ����һ���connection.getInputStream()�����вŻ���������������
            connection.connect();
            // ȡ������������ʹ��Reader��ȡ
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// ���ñ���,������������
//            System.out.println("=============================");
//            System.out.println("Contents of get request");
//            System.out.println("=============================");
            String lines = "";
            while ((reader.readLine()) != null) {
                // lines = new String(lines.getBytes(), "utf-8");
                lines = lines + reader.readLine();
            }
            reader.close();
            // �Ͽ�����
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
