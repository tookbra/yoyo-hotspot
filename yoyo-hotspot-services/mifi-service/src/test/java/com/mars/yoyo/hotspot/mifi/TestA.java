package com.mars.yoyo.hotspot.mifi;

import com.mars.yoyo.hotspot.util.DateUtil;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author tookbra
 * @date 2018/7/13
 * @description
 */
public class TestA {

    public static void main(String[] args) throws ParseException {
        System.out.println("15906691111".substring("15906695726".length() - 4));
        DateTime now = new DateTime();
        System.out.println(now.monthOfYear().getAsShortText(Locale.ENGLISH));

        SimpleDateFormat sd  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date beginDate = sd.parse("2018-08-02 08:10:00");
        Date endDate = sd.parse("2018-08-03 10:31:00");
        System.out.println(DateUtil.getDiff(endDate, beginDate));

//        long newDate1 = beginDate.getTime()/1000;
//        long newDate2 = endDate.getTime()/1000;
//        long day = (newDate2- newDate1) / (60 * 60 * 24) +1;
//
//        if(day == 1) {
//            System.out.println(DateUtil.getIntervalHour(beginDate, endDate));
//        } else {
//            DateTime beginDateTime = DateUtil.dateToDateTime(beginDate);
//            beginDateTime = beginDateTime.dayOfYear().roundCeilingCopy();
//
//            int hour1 = DateUtil.getIntervalHour(beginDate, beginDateTime.toDate());
//
//            System.out.println(hour1 * 1.2);
//
//            DateTime endDateTime = DateUtil.dateToDateTime(endDate);
//            endDateTime = endDateTime.dayOfYear().roundCeilingCopy();
//
//            int hour2 = DateUtil.getIntervalHour(endDate, endDateTime.toDate());
//            System.out.println(hour2 * 1.2);
//
//            System.out.println((day - 2)*12);
//
//
//        }

//        while (day-- > 0) {
//
//            System.out.println(1);
//        }


    }
}
