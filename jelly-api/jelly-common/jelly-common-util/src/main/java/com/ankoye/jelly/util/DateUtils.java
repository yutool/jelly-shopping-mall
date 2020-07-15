package com.ankoye.jelly.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ankoye@qq.com
 */
public class DateUtils {

    private static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat menuFormat = new SimpleDateFormat("yyyyMMddHH");

    /**
     * 获取当天时间
     * yyyy-MM-dd
     */
    public static String menuToDay(String menu) {
        try {
            return dayFormat.format(menuFormat.parse(menu));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间节点
     */
    public static String presentTime() {
        return getDateMenus().get(0);
    }

    /**
     * 获取指定日期的凌晨
     */
    public static Date toDayStartHour(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date start = calendar.getTime();
        return start;
    }

    /**
     * 时间增加N分钟
     */
    public static Date addDateMinutes(Date date,int minutes){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);// 24小时制
        date = calendar.getTime();
        return date;
    }

    /**
     * 时间递增N小时
     */
    public static Date addDateHour(Date date,int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour); // 24小时制
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取时间菜单
     * yyyyMMddHH
     */
    public static List<String> getDateMenus(){

        // 定义一个List<Date>集合，存储所有时间段
        List<Date> dates = new ArrayList<>();

        // 循环12次
        Date date = toDayStartHour(new Date()); // 凌晨
        for (int i = 0; i < 12 ; i++) {
            // 每次递增2小时,将每次递增的时间存入到List<Date>集合中
            dates.add(addDateHour(date,i * 2));
        }

        // 判断当前时间属于哪个时间范围
        Date now = new Date();
        for (Date cdate : dates) {
            //开始时间 <= 当前时间 < 开始时间+2小时
            if(cdate.getTime() <= now.getTime() && now.getTime()<addDateHour(cdate,2).getTime()){
                now = cdate;
                break;
            }
        }

        // 当前需要显示的时间菜单
        List<String> dateMenus = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            dateMenus.add(menuFormat.format(addDateHour(now,i*2)));
        }
        return dateMenus;
    }


    public static void main(String[] args) {

        // 存储数据结果
        List<Date> dateList = new ArrayList<>();

        // 获取到本日的凌晨时间点
        Date startHour = toDayStartHour(new Date());

        // 循环12次
        for(int i = 0; i < 12; i++){
            dateList.add(addDateHour(startHour,i*2));
        }

        for (Date date : dateList) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(date);
            System.out.println(format);
        }
    }


}
