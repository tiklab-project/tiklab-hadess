package com.doublekit.oms.statis.service;

import com.doublekit.member.member.dao.MemberDao;
import com.doublekit.member.member.model.Member;
import com.doublekit.oms.statis.dao.StatisticsDao;
import com.doublekit.subscribe.subscribe.model.Order;
import com.doublekit.subscribe.subscribe.model.Subscribe;
import com.doublekit.tenant.server.tenant.dao.TenantDao;
import com.doublekit.tenant.tenant.model.Tenant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService{

    @Autowired
    StatisticsDao statisticsDao;

    @Override
    public Map<String, List> statistics(Date month,String type) {

        //存放当前月天数
        List<String> dateList = new ArrayList<>();
        //存放增加数量
        List<Integer> numberList = new ArrayList<>();

        List<Member> memberList = manageDate(month, dateList, numberList,type);
        for (int i=0;i<dateList.size();i++){
            String s = dateList.get(i);
            List<Member> collect = memberList.stream().filter(a -> s.equals(a.getGroupCreateTime().substring(8, 10)) ).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)){
                Integer value = Integer.valueOf(collect.get(0).getNumber());
                if (numberList.size()>i){
                    //更新数据
                    numberList.set(i,value);
                }
            }
        }
        Map<String, List> resultMap = new HashMap<>();
        resultMap.put("day",dateList);
        resultMap.put("number",numberList);
        return resultMap;
    }

    public void addMember(){

    }


    /**
     * 时间格式处理
     * @param month 传进来月份
     * @param dateList 每月时间的list
     * @param numberList 增长数量的list
     * @param type 类型 member会员  tenant租户
     */
    public List manageDate(Date month, List<String> dateList,List<Integer> numberList,String type){
        Date nowDate = new Date();
        //年
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        //当前年
        String nowYear = year.format(nowDate);
        //传进来的年
        String yearData = year.format(month);

        //月
        SimpleDateFormat moth = new SimpleDateFormat("MM");
        //当前月
        String nowMonth = moth.format(nowDate);
        //传进来的月
        String monthData = moth.format(month);

        //日
        SimpleDateFormat day = new SimpleDateFormat("dd");
        //当前日
        String nowDay = day.format(nowDate);
        //BETWEEN  范围查询不包含结束时间
        int now = Integer.valueOf(nowDay) + 1;
        //传进来的日
        String dayData = day.format(month);

        //传进来月份的1号
        String starTime = yearData+"-"+monthData+"-01";
        String endTime;

        //当前月天数
        Integer endDay = findDay(yearData + "/" + monthData);

        addData(dateList,endDay);
        //传进来的年  月份小于当前时间
        if (Integer.valueOf(yearData)<=Integer.valueOf(nowYear)
                &&Integer.valueOf(monthData)<Integer.valueOf(nowMonth)){
            endTime = nowYear+"-"+nowMonth+"-"+endDay;
            addNumber(numberList,endDay);
        }else {
            endTime = nowYear+"-"+nowMonth+"-"+now;
            addNumber(numberList,Integer.valueOf(nowDay));
        }
        //会员
        if("member".equals(type)){
            List<Member> memberList= statisticsDao.statisticsMember(starTime,endTime);
            return memberList;
        }
        //租户
        if("tenant".equals(type)){
            List<Tenant> tenantList = statisticsDao.statisticsTenant(starTime, endTime);
            return tenantList;
        }
        //订单
        if("order".equals(type)){
            List<Order> orderList = statisticsDao.statisticsOrder(starTime, endTime);
            return orderList;
        }
        //订阅
        if ("subscribe".equals(type)){
            List<Subscribe> subscribeList = statisticsDao.statisticsSubscribe(starTime, endTime);
            return subscribeList;
        }
        return null;
    }

    /**
     * 将每月数量存放list
     * @param endDay 传进来月份的最后一天（
     */
    public void   addData(List<String> dateList,Integer endDay){
        for (int i=1;i<endDay+1;i++){
            if (i<10){
                String month="0"+i;
                dateList.add(month);
            }else {
                dateList.add(String.valueOf(i));
            }
        }
    }
    /**
     * 存放增加数量
     * @param endDay 传进来月份的最后一天（
     */
    public void   addNumber(List<Integer> numberList,Integer endDay){
        for (int i=1;i<endDay+1;i++){
            //初始化为0
            numberList.add(0);
        }
    }


    /**
     * 根据年月计算当前月天数
     * @param date
     */
    public Integer findDay(String date){
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM");
        try {
            rightNow.setTime(simpleDate.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //当前月最大天数
        int maximum = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maximum;
    }
}
