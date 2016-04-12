package qiufeng.android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/11.
 */
public class DateUtils {
    static final String formatPattern = "yyyy/MM/dd HH:mm";

    static final String formatPattern_Short = "yyyyMMdd";

    /**
     * 获取当前日期
     */
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(new Date());
    }

    /**
     * 获取制定毫秒数之前的日期
     */
    public static String getDesignatedDate(long timeDiff) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        long nowTime = System.currentTimeMillis();
        long designTime = nowTime - timeDiff;
        return format.format(designTime);
    }

    /**
     * 获取前几天的日期
     */
    public static String getPrefixDate(String count) {
        Calendar cal = Calendar.getInstance();
        int day = 0 - Integer.parseInt(count);
        cal.add(Calendar.DATE, day);   // int amount   代表天数
        Date datNew = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(datNew);
    }

    /**
     * 日期转换成字符串
     */
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(date);
    }

    public static String longToString(long time) {
        Date date = new Date();
        date.setTime(time);
        return dateToString(date);
    }

    /**
     * 字符串转换日期
     */
    public static Date stringToDate(String str) {
        //str =  " 2008-07-10 19:20:00 " 格式
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        if (!str.equals("") && str != null) {
            try {
                return format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
