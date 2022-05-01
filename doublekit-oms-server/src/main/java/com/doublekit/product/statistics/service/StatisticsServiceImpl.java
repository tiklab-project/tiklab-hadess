package com.doublekit.product.statistics.service;

import com.doublekit.core.exception.ApplicationException;
import com.doublekit.member.member.entity.MemberEntity;
import com.doublekit.member.member.model.Member;
import com.doublekit.product.statistics.dao.StatisticsDao;
import com.doublekit.product.statis.service.StatisticsService;
import com.doublekit.ssm.statis.entity.VisitEntity;
import com.doublekit.ssm.statis.model.Visit;
import com.doublekit.subscribe.order.entity.OrderEntity;
import com.doublekit.subscribe.order.model.Order;
import com.doublekit.subscribe.payment.entity.PaymentEntity;
import com.doublekit.subscribe.payment.model.Payment;
import com.doublekit.tenant.tenant.entity.TenantEntity;
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
        List<String> numberList = new ArrayList<>();

        manageDate(month,dateList,numberList,type);

        Map<String, List> resultMap = new HashMap<>();
        resultMap.put("day",dateList);
        resultMap.put("number",numberList);

        return resultMap;
    }

    @Override
    public Map<String, List> statisticsByWeek(Date month, String type) {

        //存放当前月的周数
        List<String> dateList = new ArrayList<>();

        //年格式
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        //月格式
        SimpleDateFormat moth = new SimpleDateFormat("MM");
        //传进来的年
        String yearData = year.format(month);
        //传进来的月
        String monthData = moth.format(month);
        //获取当前月天数
        Integer endDay = findDay(yearData + "/" + monthData);

        mathWeek(endDay);

        return null;
    }

    @Override
    public Map<String, List> statisticsByMonth(String year,String type) {

        Map<String, List> resultMap = new HashMap<>();

        //存放每月数量
        List<String> dateList = new ArrayList<>();

        //存放增加数量的list
        List<String> numberList = new ArrayList<>();

        Date nowDate = new Date();
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        //当前年
        String nowYear = dateYear.format(nowDate);

        SimpleDateFormat moth = new SimpleDateFormat("MM");
        //当前月
        String nowMonth = moth.format(nowDate);
        int month=0;
        if (nowYear.equals(year)){
            month=Integer.valueOf(nowMonth)+1;
        }
        if (Integer.valueOf(year)<Integer.valueOf(nowYear)){
            month=13;
        }
        if (Integer.valueOf(year)>Integer.valueOf(nowYear)){
            throw  new ApplicationException("传入的时间不能大于当前时间");
        }
        for (int a=1;a<month;a++){
            String date=null;
            dateList.add(a+"月");
            if (a<11){
                 date=year+"-0"+a;
            }else {
                date =year+"-"+a;

            }
            addNumberList(numberList,type,date);
        }

        resultMap.put("day",dateList);
        resultMap.put("number",numberList);
        return resultMap;
    }


    /**
     * 时间格式处理  日统计
     * @param month 传进来月份
     * @param dateList 每月时间的list
     * @param numberList 增长数量的list
     * @param type 类型 member会员  tenant租户 order订单   subscribe订阅
     */
    public void manageDate(Date month, List<String> dateList,List<String> numberList,String type){
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
        //支付统计
        if ("pay".equals(type)){
            List<Payment> payments = statisticsDao.statisticsPayment(starTime, endTime);
            if (CollectionUtils.isNotEmpty(payments)){
                addPaymentData(dateList,numberList,payments);
            }
        }
        if ("visit".equals(type)){
            List<Visit> visits = statisticsDao.statisticsVisit(starTime, endTime);
            if (CollectionUtils.isNotEmpty(visits)){
                addVisitData(dateList,numberList,visits);
            }
        }

    }



    /**
     * 添加每天会员数量
     * @param dateList 每月时间的list
     * @param numberList 增长数量的list
     * @param memberList 类型 member会员
     */
    public void addMemberData( List<String> dateList,List<String> numberList, List<Member> memberList ){
        for (int i=0;i<dateList.size();i++){
            String data = dateList.get(i);
            List<Member> collect = memberList.stream().filter(a -> data.equals(a.getGroupCreateTime().substring(8, 10)) ).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)){
                String number = collect.get(0).getNumber();
                if (numberList.size()>i){
                    //更新数据
                    numberList.set(i,number);
                }
            }
        }
    }
    /**
     * 添加每天租户数量
     * @param dateList 每月时间的list
     * @param numberList 增长数量的list
     * @param tenantList 类型 tenant
     */
    public void addTenantData( List<String> dateList,List<String> numberList,  List<Tenant> tenantList){
        for (int i=0;i<dateList.size();i++){
            String s = dateList.get(i);
            List<Tenant> collect = tenantList.stream().filter(a -> s.equals(a.getGroupCreateTime().substring(8, 10)) ).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)){
                String value = collect.get(0).getNumber();
                if (numberList.size()>i){
                    //更新数据
                    numberList.set(i,value);
                }
            }
        }
    }
    /**
     * 添加每天订单数量
     * @param dateList 每月时间的list
     * @param numberList 增长数量的list
     * @param orderList 类型 order
     */
    public void addOrderData( List<String> dateList,List<String> numberList,  List<Order> orderList){
        for (int i=0;i<dateList.size();i++){
            String s = dateList.get(i);
            List<Order> collect = orderList.stream().filter(a -> s.equals(a.getGroupCreateTime().substring(8, 10)) ).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)){

                String value = collect.get(0).getNumber();
                if (numberList.size()>i){
                    //更新数据
                    numberList.set(i,value);
                }
            }
        }
    }
    /**
     * 添加每天支付金额
     * @param dateList 每月时间的list
     * @param numberList 增长数量的list
     * @param paymentList 类型 Payment
     */
    public void addPaymentData( List<String> dateList,List<String> numberList,  List<Payment> paymentList){
        for (int i=0;i<dateList.size();i++){
            String s = dateList.get(i);
            List<Payment> collect = paymentList.stream().filter(a -> s.equals(a.getGroupCreateTime().substring(8, 10)) ).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)){
                String statisticsPrice = collect.get(0).getStatisticsPrice();
                if (numberList.size()>i){
                    //更新数据
                    numberList.set(i,statisticsPrice);
                }
            }
        }
    }
    /**
     * 添加每天访问
     * @param dateList 每月时间的list
     * @param numberList 增长数量的list
     * @param visitList 类型 Visit
     */
    public void addVisitData( List<String> dateList,List<String> numberList,  List<Visit> visitList){
        for (int i=0;i<dateList.size();i++){
            String s = dateList.get(i);
            List<Visit> collect = visitList.stream().filter(a -> s.equals(a.getGroupCreateTime().substring(8, 10)) ).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)){
                Integer num = collect.get(0).getNum();
                if (numberList.size()>i){
                    //更新数据
                    numberList.set(i,String.valueOf(num));
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
    public void   addNumber(List<String> numberList,Integer endDay){
        for (int i=1;i<endDay+1;i++){
            //初始化为0
            numberList.add("0");
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

    /**
     * 根据年月计算当前月周
     * @param endDay  当月天数
     */
    public void mathWeek( Integer endDay){
        //当月周数量
      int weekMath= endDay/7;
    }

    /**
     *月统计   添加每月增长数量
     *   @param numberList  每月增长数量的list
     *   @param type  类型
     * @param
     */
    public void addNumberList( List<String> numberList,String type,String time){

        //会员
        if("member".equals(type)){
            List<MemberEntity> member = statisticsDao.findMemberByLikeTime(time);
            if (CollectionUtils.isNotEmpty(member)){
                numberList.add(String.valueOf(member.size()));
            }else {
                numberList.add("0");
            }
        }
        //租户
        if("tenant".equals(type)){
            List<TenantEntity> tenant = statisticsDao.findTenantByLikeTime(time);
            if (CollectionUtils.isNotEmpty(tenant)){
                numberList.add(String.valueOf(tenant.size()));
            }else {
                numberList.add("0");
            }
        }
        //订单
        if("order".equals(type)){
            List<OrderEntity> order = statisticsDao.findOrderByLikeTime(time);
            if (CollectionUtils.isNotEmpty(order)){
                numberList.add(String.valueOf(order.size()));
            }else {
                numberList.add("0");
            }
        }
        //支付统计
        if ("pay".equals(type)){
            List<PaymentEntity> subscribe = statisticsDao.findPaymentByLikeTime(time);
            if (CollectionUtils.isNotEmpty(subscribe)){
                double anElse = subscribe.stream().map(PaymentEntity::getPayPrice).
                        mapToDouble(Double::parseDouble).reduce(Double::sum).orElse(0.00);

                numberList.add(String.valueOf(anElse));
            }else {
                numberList.add("0");
            }
        }
        //浏览统计
        if("visit".equals(type)){
            List<VisitEntity> visitByLikeTime = statisticsDao.findVisitByLikeTime(time);
            if (CollectionUtils.isNotEmpty(visitByLikeTime)){
                Integer num = visitByLikeTime.get(0).getNum();
                numberList.add(String.valueOf(num));
            }else {
                numberList.add("0");
            }
        }
    }

}
