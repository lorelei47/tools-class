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
      //  System.out.println("�����"+result);
        String[] city = {"����","�Ϻ�","����","����","�Ͼ�","�人","�ɶ�","����","����","����","���",
        		"����","֣��","��ɳ","����","����","�Ϸ�","�ൺ","����","ʯ��ׯ","����","��ݸ","����","��³ľ��"};
        
        for(String one:city){
        	String rgex = new String();
            rgex = one+"(.*?)��";           
            System.out.println(one+":"+getSubUtilSimple(result, rgex));
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
}
