package com.doublekit.oms.statistics.service;

import com.doublekit.member.member.model.Member;
import com.doublekit.oms.statistics.dao.StatisticsDao;
import com.doublekit.oms.statis.service.StatisticsService;
import com.doublekit.subscribe.order.model.Order;
import com.doublekit.subscribe.subscribe.model.Subscribe;
import com.doublekit.tenant.tenant.model.Tenant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    StatisticsDao statisticsDao;

    @Override
    public Map<String, List> statistics(Date month,String type) {

        //存放当前月天数的list
        List<String> dateList = new ArrayList<>();
        //存放增加数量的list
        List<Integer> numberList = new ArrayList<>();

        manageDate(month,dateList,numberList,type);

        Map<String, List> resultMap = new HashMap<>();
        resultMap.put("day",dateList);
        resultMap.put("number",numberList);
        return resultMap;
    }




    /**
     * 时间格式处理
     * @param month 传进来月份
     * @param dateList 每月时间的list
     * @param numberList 增长数量的list
     * @param type 类型 member会员  tenant租户 order订单   subscribe订阅
     */
    public void manageDate(Date month, List<String> dateList,List<Integer> numberList,String type){
        Date nowDate = new Date();
        //年格式
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        //当前年
        String nowYear = year.format(nowDate);
        //传进来的年
        String yearData = year.format(month);

        //月格式
        SimpleDateFormat moth = new SimpleDateFormat("MM");
        //当前月
        String nowMonth = moth.format(nowDate);
        //传进来的月
        String monthData = moth.format(month);

        //日格式
        SimpleDateFormat day = new SimpleDateFormat("dd");
        //当前日
        String nowDay = day.format(nowDate);
        //BETWEEN  范围查询不包含结束时间
        int now = Integer.valueOf(nowDay) + 1;

        //获取传进来月份的1号
        String starTime = yearData+"-"+monthData+"-01";

        String endTime;
        //获取当前月天数
        Integer endDay = findDay(yearData + "/" + monthData);
        addCurrentMonthDay(dateList,endDay);
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
            addMemberData(dateList,numberList,memberList);
        }
        //租户
        if("tenant".equals(type)){
            List<Tenant> tenantList = statisticsDao.statisticsTenant(starTime, endTime);
            addTenantData(dateList,numberList,tenantList);
        }
        //订单
        if("order".equals(type)){
            List<Order> orderList = statisticsDao.statisticsOrder(starTime, endTime);
            addOrderData(dateList,numberList,orderList);

        }
        //订阅
        if ("subscribe".equals(type)){
            List<Subscribe> subscribeList = statisticsDao.statisticsSubscribe(starTime, endTime);
            if (CollectionUtils.isNotEmpty(subscribeList)){
                addSubscribeData(dateList,numberList,subscribeList);
            }
        }

    }
    /**
     * 添加会员数据
     * @param dateList 每月时间的list
     * @param numberList 增长数量的list
     * @param memberList 类型 member会员
     */
    public void addMemberData( List<String> dateList,List<Integer> numberList, List<Member> memberList ){
        for (int i=0;i<dateList.size();i++){
            String data = dateList.get(i);
            List<Member> collect = memberList.stream().filter(a -> data.equals(a.getGroupCreateTime().substring(8, 10)) ).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)){
                Integer value = Integer.valueOf(collect.get(0).getNumber());
                if (numberList.size()>i){
                    //更新数据
                    numberList.set(i,value);
                }
            }
        }
    }
    /**
     * 添加租户数据
     * @param dateList 每月时间的list
     * @param numberList 增长数量的list
     * @param tenantList 类型 tenant
     */
    public void addTenantData( List<String> dateList,List<Integer> numberList,  List<Tenant> tenantList){
        for (int i=0;i<dateList.size();i++){
            String s = dateList.get(i);
            List<Tenant> collect = tenantList.stream().filter(a -> s.equals(a.getGroupCreateTime().substring(8, 10)) ).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)){
                Integer value = Integer.valueOf(collect.get(0).getNumber());
                if (numberList.size()>i){
                    //更新数据
                    numberList.set(i,value);
                }
            }
        }
    }
    /**
     * 添加订单
     * @param dateList 每月时间的list
     * @param numberList 增长数量的list
     * @param orderList 类型 order
     */
    public void addOrderData( List<String> dateList,List<Integer> numberList,  List<Order> orderList){
        for (int i=0;i<dateList.size();i++){
            String s = dateList.get(i);
            List<Order> collect = orderList.stream().filter(a -> s.equals(a.getGroupCreateTime().substring(8, 10)) ).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)){
                Integer value = Integer.valueOf(collect.get(0).getNumber());
                if (numberList.size()>i){
                    //更新数据
                    numberList.set(i,value);
                }
            }
        }
    }
    /**
     * 添加订阅
     * @param dateList 每月时间的list
     * @param numberList 增长数量的list
     * @param subscribeList 类型 subscribe
     */
    public void addSubscribeData( List<String> dateList,List<Integer> numberList,  List<Subscribe> subscribeList){
        for (int i=0;i<dateList.size();i++){
            String s = dateList.get(i);
            List<Subscribe> collect = subscribeList.stream().filter(a -> s.equals(a.getGroupCreateTime().substring(8, 10)) ).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)){
                Integer value = Integer.valueOf(collect.get(0).getNumber());
                if (numberList.size()>i){
                    //更新数据
                    numberList.set(i,value);
                }
            }
        }
    }


    /**
     * 将每月数量存放list
     * @param endDay 传进来月份的最后一天（
     */
    public void  addCurrentMonthDay(List<String> dateList,Integer endDay){
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