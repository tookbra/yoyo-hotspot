package com.mars.yoyo.hotspot.util;

import com.mars.yoyo.hotspot.constant.DatePattern;
import com.mars.yoyo.hotspot.exception.DateException;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间工具类
 * @author tookbra
 * @date 2018/4/6
 * @description
 */
public class DateUtil extends DateUtils {

    public final static String DATE_FORMAT = "yyyyMMddHHmmss";
    public final static long TIMESTAMP = 1000;

    /**
     * 时间戳正则表达式
     */
    public final static String TIMESTAMP_LEN_REP1 = "^\\d{10}$";

    public final static String TIMESTAMP_LEN_REP2 = "^\\d{13}$";

    /**
     * 验证时间戳格式是否正确
     * @param timeStamp 时间戳
     * @return
     */
    public static boolean validateTimeStamp(String timeStamp) {
        if(isValid(timeStamp, TIMESTAMP_LEN_REP1)) {
            return true;
        }

        if(isValid(timeStamp, TIMESTAMP_LEN_REP2)) {
            return true;
        }

        return false;
    }

    /**
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long diffTimeStamp(long time1, long time2) {
        return time1 - time2;
    }

    public static int getTimeStamp() {
        return (int)(System.currentTimeMillis() / 1000);
    }

    /**
     * 获取两个日期相差的秒数
     *
     * @param bDate
     *            日期字符串
     * @param eDate
     *            另一个日期字符串
     * @return
     */
    public static int getIntervalSec(Date bDate, Date eDate) {
        int num = -1;
        if (bDate != null && eDate != null) {
            long time = Math.abs(eDate.getTime() - bDate.getTime());
            num = (int) (time / 1000);
        }
        return num;
    }

    /**
     * 获取两个日期相差的分钟
     *
     * @param bDate
     *            日期字符串
     * @param eDate
     *            另一个日期字符串
     * @return
     */
    public static int getAbsIntervalMin(Date bDate, Date eDate) {
        int num = -1;
        if (bDate != null && eDate != null) {
            long time = Math.abs(eDate.getTime() - bDate.getTime());
            num = (int) (time / (60 * 1000));
        }
        return num;
    }

    /**
     * 获取两个日期相差的分钟
     *
     * @param bDate
     *            日期字符串
     * @param eDate
     *            另一个日期字符串
     * @return
     */
    public static int getIntervalMin(Date bDate, Date eDate) {
        int num = -1;
        if (bDate != null && eDate != null) {
            long time = eDate.getTime() - bDate.getTime();
            num = (int) (time / (60 * 1000));
        }
        return num;
    }


    public static int getIntervalFloorlHour(Date bDate, Date eDate) {
        int num = -1;
        if (bDate != null && eDate != null) {
            long time = Math.abs(eDate.getTime() - bDate.getTime());
            num = (int)Math.floor(time / (60  * 60 * 1000d));
        }
        return num;
    }

    public static int getIntervalHour(Date bDate, Date eDate) {
        int num = -1;
        if (bDate != null && eDate != null) {
            long time = Math.abs(eDate.getTime() - bDate.getTime());
            num = (int)Math.ceil(time / (60  * 60 * 1000d));
        }
        return num;
    }

    public static int getIntervalDay(Date bDate, Date eDate) {
        int num = -1;
        if (bDate != null && eDate != null) {
            long time = Math.abs(eDate.getTime() - bDate.getTime());
            num = (int) (time / (24 * 60 * 60 * 1000));
        }
        return num;
    }

    public static Map<String, Long> getDiff(Date bDate, Date eDate) {
        Map<String, Long> map = new HashMap<>(4);
        try {
            long diff = bDate.getTime() - eDate.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
            map.put("day", days);
            map.put("hour", hours);
            map.put("min", minutes);
            map.put("sec", seconds);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

    /**
     * 正则验证
     * @param sourceStr 需要验证的字符串
     * @param patternStr 正则表达式
     * @return
     */
    private static boolean isValid(String sourceStr, String patternStr){
        Matcher matcher = Pattern.compile(patternStr).matcher(sourceStr);
        return matcher.matches();
    }


    /**
     * 格式化日期 yyyyMMddHHmmss
     * @param date 被格式化的日期时间
     * @return 格式化后的日期时间
     */
    public static String formatPureDateTime(Date date) {
        return formatDateTime(date, DatePattern.PURE_DATETIME_PATTERN);
    }

    /**
     * 格式化日期 yyyyMMdd
     * @param date 被格式化的日期时间
     * @return 格式化后的日期时间
     */
    public static String formatDate(Date date) {
        return formatDateTime(date, DatePattern.DATE_PATTERN);
    }

    /**
     * 格式化日期时间
     * @param date 被格式化的日期时间
     * @param pattern 格式
     * @return 格式化后的日期时间
     */
    public static String formatDateTime(Date date, String pattern) {
        if (null == date) {
            return null;
        }
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 字符串转Date yyyyMMssHHmmss
     * @param dateStr
     * @return
     */
    public static Date parsePureDateTime(String dateStr) {
        return parseDate(dateStr, DatePattern.PURE_DATETIME_PATTERN);
    }

    /**
     * 字符串转Date
     * @param dateStr Date字符串
     * @param format 格式
     * @return
     */
    public static Date parseDate(String dateStr, String format) {
        return parseDate(dateStr, new SimpleDateFormat(format));
    }

    /**
     * 字符串转Date
     * @param dateStr Date字符串
     * @param dateFormat 格式化器 {@link SimpleDateFormat}
     * @return
     */
    public static Date parseDate(String dateStr, DateFormat dateFormat) {
        return parse(dateStr, dateFormat);
    }

    /**
     * 字符串转Date
     * @param dateStr
     * @param dateFormat
     * @return
     */
    public static Date parse(String dateStr, DateFormat dateFormat) {
        try {
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            String pattern;
            if (dateFormat instanceof SimpleDateFormat) {
                pattern = ((SimpleDateFormat) dateFormat).toPattern();
            } else {
                pattern = dateFormat.toString();
            }
            throw new DateException(MessageFormat.format("Parse [{0}] with format [{1}] error!", dateStr, pattern), e);
        }
    }


    /**
     * 时间戳差
     * @param timestamp
     * @return 差值
     */
    public static long nowSubSecond(long timestamp) {
        long now = System.currentTimeMillis();
        return (now - timestamp) / 1000;
    }

    public static DateTime dateToDateTime(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime;
    }

    /**
     * 判断是否本月
     * @param dateTime
     * @return
     */
    public static boolean isThisMonth(DateTime dateTime) {
        if(dateTime.getMonthOfYear() == DateTime.now().getMonthOfYear()) {
            return true;
        }
        return false;
    }

    public static DateTime zero(DateTime dateTime) {
        dateTime.withHourOfDay(0);
        dateTime.withMinuteOfHour(0);
        dateTime.withMillisOfSecond(0);
        return dateTime;
    }


    public static void main(String[] args) {
        String dateStr = "2018-06-22 10:00:00";
        Date date = parseDate(dateStr, DatePattern.PURE_DATETIME_PATTERN1);
        System.out.println(getIntervalHour(date, DateTime.now().toDate()));
    }
}
