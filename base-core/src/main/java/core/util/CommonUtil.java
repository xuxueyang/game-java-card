package core.util;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * 工具类
 * 2018-07-19
 */
public final class CommonUtil {
    /**
     * @param belongDate
     * @return 所属日期的零点时间
     */
    public static ZonedDateTime tranferBelongDateToZoneDate(String belongDate) {
        if (Validators.verifyBelongDate(belongDate)) {
            //如果字段不正确，默认返回当前
            String[] split = belongDate.split("-");
            try {
                return ZonedDateTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]),
                    0, 0, 0, 0, ZonedDateTime.now().getZone());
            } catch (Exception e) {
                //说明时间转换错误，返回当前日期
                ZonedDateTime tmp = ZonedDateTime.now();
                return ZonedDateTime.of(tmp.getYear(), tmp.getMonthValue(), tmp.getDayOfMonth(),
                    0, 0, 0, 0, tmp.getZone());
            }

        } else {
            //如果字段不正确，默认返回当前
            ZonedDateTime tmp = ZonedDateTime.now();
            return ZonedDateTime.of(tmp.getYear(), tmp.getMonthValue(), tmp.getDayOfMonth(),
                0, 0, 0, 0, tmp.getZone());
        }
    }

    public static String getTodayBelongDate() {
//        ZonedDateTime tmp = ZonedDateTime.now();
//        StringBuffer sb = new StringBuffer();
//        sb.append(tmp.getYear());
//        sb.append("-");
//        if(tmp.getMonthValue()<9){
//            sb.append("0");
//            sb.append(tmp.getMonthValue());
//        }else{
//            sb.append(tmp.getMonthValue());
//        }
//        sb.append("-");
//        if(tmp.getDayOfMonth()<9){
//            sb.append("0");
//            sb.append(tmp.getDayOfMonth());
//        }else {
//            sb.append(tmp.getDayOfMonth());
//        }
//        return sb.toString();
        return formatDateToBelongDate(new Date());
    }

    public static ZonedDateTime getBelongDateNextZoneDay(String belongDate) {
        ZonedDateTime zonedDateTime = tranferBelongDateToZoneDate(belongDate);
        return zonedDateTime.plusDays(1);
    }

    public static void main(String[] args) {
        String belongDate = formatDateToBelongDate(new Date());
        System.out.println(belongDate);
    }

    public static String formatDateToBelongDate(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sf.format(date);
        return format;
    }

    /**
     * 排序map
     *
     * @param logMap
     */
    public static Map sortMap(Map logMap) {
        if (logMap == null || logMap.isEmpty()) {
            return logMap;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        sortMap.putAll(logMap);
        return sortMap;
    }
    //实现一个比较器类

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);  //从小到大排序
        }

    }
}

