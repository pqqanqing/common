package com.wjs.common.base.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by pqq on 2015/8/10.
 */
public class DateUtil {
    public static final String DATAFORMAT_STR = "yyyy-MM-dd";

    public static final String YYYY_MM_DATAFORMAT_STR = "yyyy-MM";

    public static final String DATATIMEF_STR = "yyyy-MM-dd HH:mm:ss";

    public static final String ZHCN_DATAFORMAT_STR = "yyyy年MM月dd日";

    public static final String ZHCN_DATATIMEF_STR = "yyyy年MM月dd日HH时mm分ss秒";

    public static final String ZHCN_DATATIMEF_STR_4yMMddHHmm = "yyyy年MM月dd日HH时mm分";

    private static DateFormat dateFormat = null;

    private static DateFormat dateTimeFormat = null;

    private static DateFormat zhcnDateFormat = null;

    private static DateFormat zhcnDateTimeFormat = null;

    static {
        dateFormat = new SimpleDateFormat(DATAFORMAT_STR);
        dateTimeFormat = new SimpleDateFormat(DATATIMEF_STR);
        zhcnDateFormat = new SimpleDateFormat(ZHCN_DATAFORMAT_STR);
        zhcnDateTimeFormat = new SimpleDateFormat(ZHCN_DATATIMEF_STR);
    }

    private static DateFormat getDateFormat(String formatStr) {
        if (formatStr.equalsIgnoreCase(DATAFORMAT_STR)) {
            return dateFormat;
        } else if (formatStr.equalsIgnoreCase(DATATIMEF_STR)) {
            return dateTimeFormat;
        } else if (formatStr.equalsIgnoreCase(ZHCN_DATAFORMAT_STR)) {
            return zhcnDateFormat;
        } else if (formatStr.equalsIgnoreCase(ZHCN_DATATIMEF_STR)) {
            return zhcnDateTimeFormat;
        } else {
            return new SimpleDateFormat(formatStr);
        }
    }

    public static Date getDate(String dateTimeStr) {
        return getDate(dateTimeStr, DATATIMEF_STR);
    }

    public static Date getDate(String dateTimeStr, String formatStr) {
        try {
            if (dateTimeStr == null || dateTimeStr.equals("")) {
                return null;
            }
            DateFormat sdf = getDateFormat(formatStr);
            Date d = sdf.parse(dateTimeStr);
            return d;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date transferDate(String date) throws Exception {
        if (date == null || date.length() < 1) return null;
        if (date.length() != 8) throw new Exception("日期格式错误");
        String con = "-";
        String yyyy = date.substring(0, 4);
        String mm = date.substring(4, 6);
        String dd = date.substring(6, 8);
        int month = Integer.parseInt(mm);
        int day = Integer.parseInt(dd);
        if (month < 1 || month > 12 || day < 1 || day > 31) throw new Exception("日期格式错误");
        String str = yyyy + con + mm + con + dd;
        return DateUtil.getDate(str, DateUtil.DATAFORMAT_STR);
    }

    public static String dateToDateString(Date date) {
        return dateToDateString(date, DATATIMEF_STR);
    }

    public static String dateToDateString(Date date, String formatStr) {
        DateFormat df = getDateFormat(formatStr);
        return df.format(date);
    }

    public static String getTimeString(String dateTime) {
        return getTimeString(dateTime, DATATIMEF_STR);
    }

    public static String getTimeString(String dateTime, String formatStr) {
        Date d = getDate(dateTime, formatStr);
        String s = dateToDateString(d);
        return s.substring(DATATIMEF_STR.indexOf('H'));
    }

    public static String getCurDate() {
        return dateToDateString(Calendar.getInstance().getTime(), DATAFORMAT_STR);
    }

    public static String getCurZhCNDate() {
        return dateToDateString(new Date(), ZHCN_DATAFORMAT_STR);
    }

    public static String getCurDateTime() {
        return dateToDateString(new Date(), DATATIMEF_STR);
    }

    public static String getCurZhCNDateTime() {
        return dateToDateString(new Date(), ZHCN_DATATIMEF_STR);
    }

    public static Date getInternalDateByDay(Date d, int days) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.DATE, days);
        return now.getTime();
    }

    public static Date getInternalDateByMon(Date d, int months) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.MONTH, months);
        return now.getTime();
    }

    public static Date getInternalDateByYear(Date d, int years) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.YEAR, years);
        return now.getTime();
    }

    public static Date getInternalDateBySec(Date d, int sec) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.SECOND, sec);
        return now.getTime();
    }

    public static Date getInternalDateByMin(Date d, int min) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.MINUTE, min);
        return now.getTime();
    }

    public static Date getInternalDateByHour(Date d, int hours) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.HOUR_OF_DAY, hours);
        return now.getTime();
    }

    public static String getZhCNDateTime(String dateStr) {
        Date d = getDate(dateStr);
        return dateToDateString(d, ZHCN_DATATIMEF_STR);
    }

    public static String getZhCNDate(String dateStr) {
        Date d = getDate(dateStr, DATAFORMAT_STR);
        return dateToDateString(d, ZHCN_DATAFORMAT_STR);
    }

    public static String getDateStr(String dateStr, String fmtFrom, String fmtTo) {
        Date d = getDate(dateStr, fmtFrom);
        return dateToDateString(d, fmtTo);
    }

    public static long compareDateStr(String time1, String time2) {
        Date d1 = getDate(time1);
        Date d2 = getDate(time2);
        return d2.getTime() - d1.getTime();
    }

    public static long getMicroSec(BigDecimal hours) {
        BigDecimal bd;
        bd = hours.multiply(new BigDecimal(3600 * 1000));
        return bd.longValue();
    }

    public static int getMin(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MINUTE);
    }

    public static int getHour(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.HOUR_OF_DAY);
    }

    public static int getSecond(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.SECOND);
    }

    public static int getDay(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonth(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MONTH) + 1;
    }

    public static int getYear(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.YEAR);
    }

    public static String getYearMonthOfLastMon(Date d) {
        Date newdate = getInternalDateByMon(d, -1);
        String year = String.valueOf(getYear(newdate));
        String month = String.valueOf(getMonth(newdate));
        return year + month;
    }

    public static String getCurYearMonth() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        String DATE_FORMAT = "yyyyMM";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    public static Date getNextMonth(String year, String month) {
        String datestr = year + "-" + month + "-01";
        Date date = getDate(datestr, DATAFORMAT_STR);
        return getInternalDateByMon(date, 1);
    }

    public static Date getLastMonth(String year, String month) {
        String datestr = year + "-" + month + "-01";
        Date date = getDate(datestr, DATAFORMAT_STR);
        return getInternalDateByMon(date, -1);
    }

    public static String getSingleNumDate(Date d) {
        return dateToDateString(d, DATAFORMAT_STR);
    }

    public static String getHalfYearBeforeStr(Date d) {
        return dateToDateString(getInternalDateByMon(d, -6), DATAFORMAT_STR);
    }

    public static String getInternalDateByLastDay(Date d, int days) {
        return dateToDateString(getInternalDateByDay(d, days), DATAFORMAT_STR);
    }

    public static int getCurentMonthDay() {
        Date date = Calendar.getInstance().getTime();
        return getMonthDay(date);
    }

    public static int getMonthDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getMonthDay(String date) {
        Date strDate = getDate(date, DATAFORMAT_STR);
        return getMonthDay(strDate);
    }

    public static String getStringDate(Calendar cal) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(cal.getTime());
    }

    //是否超时
    public static boolean isOverMinute(Long time, int i) {
        return time + i * 60 * 1000 < new Date().getTime();
    }
}