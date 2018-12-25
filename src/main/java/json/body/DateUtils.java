package json.body;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public final class DateUtils {

    public static final int DATE_PATTERN_LEN = 10;
    public static final int DATE_TIME_PATTERN_LEN = 19;
    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT_DEFAULT = "hh:mm:ss:SSSZ";
    private static SimpleDateFormat df_date = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
    private static SimpleDateFormat df_date_time = new SimpleDateFormat(DATE_TIME_FORMAT_DEFAULT);
    private static SimpleDateFormat df_time = new SimpleDateFormat(TIME_FORMAT_DEFAULT);

    public DateUtils() {
    }

    public static String formatDate(Date d) {
        return ((SimpleDateFormat) df_date.clone()).format(d);
    }

    public static String formatTime(Date d) {
        return ((SimpleDateFormat) df_time.clone()).format(d);
    }

    public static String formatDateTime(Date d) {
        return ((SimpleDateFormat) df_date_time.clone()).format(d);
    }

    public static Date parseDate(String d) throws ParseException {
        return ((SimpleDateFormat) df_date.clone()).parse(d);
    }

    public static Date parseDateTime(String d) throws ParseException {
        return ((SimpleDateFormat) df_date_time.clone()).parse(d);
    }

    /**
     * 根据日期长度自动格式化成日期对象
     * @param source
     * @return
     * @throws ParseException
     */
    public static Date parseDateTimeAuto(String source) throws ParseException {
        return parseDateTimeAuto(source,0);
    }


    /**
     * 根据日期长度自动格式化成日期对象
     * @param source
     * @param addDay (日期格式时才有效,主要用于日期格式时，生成的结束时间做为查询条件）
     * @return
     * @throws ParseException
     */
    public static Date parseDateTimeAuto(String source,int addDay) throws ParseException {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        int len = source.length();
        //日期时间格式
        if (len == DATE_TIME_PATTERN_LEN) {
            return parseDateTime(source);
        } else {
            //默认按日期格式
            return addDay>0?addDay(parseDate(source),addDay):parseDate(source);
        }
    }

    /**
     * 在当前的日期上添加多少天
     * @param date
     * @param addDay
     * @return
     */
    public static Date addDay(Date date,int addDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH,addDay);
        return c.getTime();
    }

    public static String formatDateTime(Date d,String pattern) {
        return (new SimpleDateFormat(pattern)).format(d);
    }
}
