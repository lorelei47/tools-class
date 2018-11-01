import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class getMetroData{
	
	public static void main(String[] args) {  
        String fileName = "D:/software/Myeclipse/Noname1.txt";
        
        String result = readToString(fileName);
      //  System.out.println("结果："+result);
        String[] city = {"北京","上海","广州","深圳","南京","武汉","成都","西安","重庆","杭州","天津",
        		"苏州","郑州","长沙","南宁","昆明","合肥","青岛","宁波","石家庄","福州","东莞","厦门","乌鲁木齐"};
        
        for(String one:city){
        	String rgex = new String();
            rgex = one+"(.*?)万";           
            System.out.println(one+":"+getSubUtilSimple(result, rgex));
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
}
