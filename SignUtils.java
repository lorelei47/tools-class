import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.http.util.TextUtils;
/*
 * 摩点接口签名生成
 */
public class SignUtils {
	private static final String P = "das41aq6";
    private SortUtils<String> sortUtils;
    public String getSign(String baseUrl, HashMap<String, String> parameters) {
        List<String> keySet = new ArrayList<String>();
        keySet.addAll(parameters.keySet());
        sortUtils = new SortUtils<String>();
        sortUtils.sort(keySet, "toString", null);
        String value = "";
        StringBuffer stringBuffer = new StringBuffer();
        if (keySet.size() > 0) {
            for (int i = 0; i < keySet.size(); i++) {
                if (i != 0) {
                    stringBuffer.append("&");
                }
                value = parameters.get(keySet.get(i));
                if (null == value || "null".equalsIgnoreCase(value)) {
                    value = "";
                }
                if (!TextUtils.isEmpty(value.trim())) {
                    stringBuffer.append(keySet.get(i));
                    stringBuffer.append("=");
                    stringBuffer.append(parameters.get(keySet.get(i)));
                }
            }
        }
        return encrypt(baseUrl, stringBuffer.toString());
    }
    public static String encrypt(String url, String params) {
        String str = params + "&p=" + P;
        str = MD5(str);
        return str.substring(5, 21);
    }
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
    public class SortUtils<E> {
        public void sort(List<E> list, final String method, final String sort) {
            Collections.sort(list, new Comparator() {
                public int compare(Object a, Object b) {
                    int ret = 0;
                    try {
                        Method m1 = ((E) a).getClass().getMethod(method, new Class[0]);
                        Method m2 = ((E) b).getClass().getMethod(method, new Class[0]);
                        if (sort != null && "desc".equals(sort)) {
                            ret = m2.invoke(b, new Object[]{}).toString()
                                    .compareTo(m1.invoke(a, new Object[]{}).toString());
                        } else {
                            ret = m1.invoke(a, new Object[]{}).toString()
                                    .compareTo(m2.invoke(b, new Object[]{}).toString());
                        }
                    } catch (NoSuchMethodException ne) {
                        ne.printStackTrace();
                    } catch (IllegalAccessException ie) {
                        ie.printStackTrace();
                    } catch (InvocationTargetException it) {
                        it.printStackTrace();
                    }
                    return ret;
                }
            });
        }
    }
}
