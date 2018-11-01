import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * ������ʽƥ������ָ���ַ����м������ 
 * @param soap �ı�
 * @param rgex ����
 * @return 
 */ 
public class getSubUtil {
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

}
