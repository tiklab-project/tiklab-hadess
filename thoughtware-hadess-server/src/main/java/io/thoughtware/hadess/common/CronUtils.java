package io.thoughtware.hadess.common;

import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.hadess.timedtask.model.TimeTaskInstance;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * cron表达式工具类
 */

public class CronUtils {

    /**
     * 获取core表达式
     * @param weekDay 周数
     * @param data 时间 时分秒
     * @return cron
     */
    public  static String getCron(Integer weekDay,String data)  {
        try {
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();
            // 获取当前属于的星期几的阿拉伯数字
            int currentDayOfWeek = currentDate.getDayOfWeek().getValue();

            String execData=null;

            //定时任务的周数大于当前周数
            if (weekDay>currentDayOfWeek){
                //相差天数
                int differWeek = weekDay - currentDayOfWeek;
                //加上相差时间后的日期
                LocalDate newDate = currentDate.plusDays(differWeek);
                execData = String.valueOf(newDate);
            }

            //定时任务的周数小于当前周数
            if (weekDay<currentDayOfWeek){
                //相差天数
                int differWeek = 7-currentDayOfWeek+weekDay;

                //加上相差时间后的日期
                LocalDate newDate = currentDate.plusDays(differWeek);
                execData = String.valueOf(newDate);
            }

            //定时任务的周数等于当前周数
            if (weekDay==currentDayOfWeek){
                //当前时间
                long currentTimeMillis = System.currentTimeMillis();

                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String format = simpleDateFormat.format(date);

                SimpleDateFormat allSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date parse = allSimpleDateFormat.parse(format + " "+data);

                execData=format;
                //设置的时间小于当前时间
                if (parse.getTime()<currentTimeMillis){
                    LocalDate newDate = currentDate.plusDays(7);
                    execData = String.valueOf(newDate);
                }
            }
            SimpleDateFormat allSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date parse = allSimpleDateFormat.parse(execData + " "+data);

            String dtFormat= "ss mm HH dd MM ? yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(dtFormat);
            return dateFormat.format(parse);
        }catch (ParseException e){
            throw new ApplicationException("时间格式转换错误，错误时间："+e);
        }
    }



    /**
     * 周几装换成具体日期
     * @param cron 表达式
     * @return 日期
     */
    public static Date weekTime(String cron){
        try {
            // 解析cron表达式为日期对象
            SimpleDateFormat dateFormat = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
            return dateFormat.parse(cron);
        } catch (ParseException e) {
            throw new RuntimeException("core表达式日志去转化错误："+e);
        }
    }

    /**
     * 将date 转化为string
     * @param date date
     * @return 日期
     */
    public static String dateChangeString(Date date){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return   simpleDateFormat.format(date);
    }


    /**
     * 替换周末
     * @param weekList
     * @return 日期
     */
    public static String replaceWeek(List<Integer> weekList){
        String[] weekDays = {"今天", "周一", "周二", "周三", "周四", "周五", "周六","周日"};

        String weekName=null;
        for (int i=0;i<weekList.size();i++){
            Integer week = weekList.get(i);
            if (i==0){
                weekName=weekDays[week];
                continue;
            }
             weekName =weekName+" | "+weekDays[week];
        }

        return weekName;
    }


}

























