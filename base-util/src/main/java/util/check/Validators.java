package util.check;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {

    public static boolean fieldMaxLength(Object obj, int maxLength) {
        if (fieldNotBlank(obj)) {
            int length = String.valueOf(obj).length();
            return length <= maxLength;
        }
        return false;
    }

    public static boolean fieldMinLength(Object obj, int minLength) {
        if (fieldNotBlank(obj)) {
            int length = String.valueOf(obj).length();
            return length >= minLength;
        }
        return false;
    }

    public static boolean fieldFormatWithPattern(String fieldValue, String regex) {
        if (StringUtils.isNotEmpty(fieldValue)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(fieldValue);
            return matcher.matches();
        }
        return false;
    }

    public static boolean fieldNotBlank(Object obj) {
        return obj != null && StringUtils.isNotBlank(String.valueOf(obj));
    }

    public static boolean verifyBelongDate(Object obj){
        if(obj==null||"".equals(String.valueOf(obj))){
            return false;
        }
        //¿‡À∆ 2018-06-02
        String pattern = "\\d{4}-\\d{2}-\\d{2}";
        return Pattern.matches(pattern,String.valueOf(obj));
    }
    public static boolean verifyFileName(Object obj){
        if(obj==null||"".equals(String.valueOf(obj))){
            return false;
        }
        //¿‡À∆ 2018-06-02
        if(obj.toString().contains("\\\\")
                ||obj.toString().contains("/")
                ||obj.toString().startsWith(".")){
            return false;
        }
        return true;
    }
    public static boolean fieldBlank(Object obj) {
        return obj == null || StringUtils.isBlank(String.valueOf(obj));
    }


    public static boolean fieldNotEmpty(Object obj) {
        return obj != null && StringUtils.isNotEmpty(String.valueOf(obj));
    }

    public static boolean fieldEmpty(Object obj) {
        return obj == null || StringUtils.isEmpty(String.valueOf(obj));
    }

    /**
     * ????????ßπ??¶∂???????true,???fieldValue?null?????????fieldRangeValues?null
     * @param fieldValue      ????
     * @param fieldRangeValues ??ßπ??¶∂
     * @return
     */
    public static <T> boolean fieldRangeValue(T fieldValue, T... fieldRangeValues) {
        if(fieldRangeValues == null){
            return false;
        }

        if(fieldValue != null){
            for(T fieldRangeValue:fieldRangeValues){
                if(fieldValue.equals(fieldRangeValue)){
                    return true;
                }
            }
        }else{
            for(T fieldRangeValue:fieldRangeValues){
                if(fieldRangeValue == null){
                    return true;
                }
            }
        }
        return false;
    }

}
