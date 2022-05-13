package com.doublekit.product.statistics.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.dal.jpa.criterial.condition.QueryCondition;
import com.doublekit.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import com.doublekit.member.member.entity.MemberEntity;
import com.doublekit.member.member.model.Member;
import com.doublekit.product.product.entity.ProductEntity;

import com.doublekit.statistics.visit.entity.VisitEntity;
import com.doublekit.statistics.visit.model.Visit;
import com.doublekit.subscribe.order.entity.OrderEntity;
import com.doublekit.subscribe.order.model.Order;
import com.doublekit.subscribe.payment.entity.PaymentEntity;
import com.doublekit.subscribe.payment.model.Payment;
import com.doublekit.subscribe.subscribe.entity.SubscribeEntity;
import com.doublekit.subscribe.subscribe.model.Subscribe;
import com.doublekit.tenant.tenant.entity.TenantEntity;
import com.doublekit.tenant.tenant.model.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticsDao {
    private static Logger logger = LoggerFactory.getLogger(StatisticsDao.class);

    @Autowired
    JpaTemplate jpaTemplate;
    /**
     * 通过创建时间段统计 会员数量
     * @param starTime 开始时间
     * @param endTime  结束时间
     */
    public List<Member> statisticsMember(String starTime, String endTime) {

        String  sql = "select group_create_time, sum(1)  as math from mem_member  where create_time BETWEEN  ? and  ? GROUP BY group_create_time";
        List<Member> memberList = new ArrayList();
        getJdbcTemplate().query(sql, new Object[]{starTime, endTime},  new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                Member member = new Member();
                member.setNumber(rs.getString("math"));
                member.setGroupCreateTime(rs.getString("group_create_time"));
                memberList.add(member);
            }
        });
        return memberList;
    }

    /**
     * 通过创建时间段统计 租户数量
     * @param starTime 开始时间
     * @param endTime 结束时间
     */
    public List<Tenant> statisticsTenant(String starTime, String endTime) {

        String  sql = "select group_create_time, sum(1)  as math from tec_tenant  where create_time BETWEEN  ? and  ? GROUP BY group_create_time";
        List<Tenant> tenantList = new ArrayList();
        getJdbcTemplate().query(sql, new Object[]{starTime, endTime},  new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                Tenant tenant = new Tenant();
                tenant.setNumber(rs.getString("math"));
                tenant.setGroupCreateTime(rs.getString("group_create_time"));
                tenantList.add(tenant);
            }
        });
        return tenantList;
    }

    /**
     * 通过创建时间段统计 订单数量 （统计的正式订单和已完成的订单）
     * @param starTime 开始时间
     * @param endTime 结束时间
     */
    public List<Order> statisticsOrder(String starTime, String endTime) {

        String  sql = "select group_create_time, sum(1)  as math from trc_order  where subscribe_type=2 and payment_status=2 and create_time BETWEEN  ? and  ? GROUP BY group_create_time";
        List<Order> orderList = new ArrayList();
        getJdbcTemplate().query(sql, new Object[]{starTime, endTime},  new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                Order order = new Order();
                order.setNumber(rs.getString("math"));
                order.setGroupCreateTime(rs.getString("group_create_time"));
                orderList.add(order);
            }
        });
        return orderList;
    }

    /**
     * 通过创建时间段统计 支付 （统计的支付的金额）
     * @param starTime 开始时间
     * @param endTime 结束时间
     */
    public List<Payment> statisticsPayment(String starTime, String endTime) {
        String  sql = "select group_create_time, sum(pay_price)  as math from trc_payment  where pay_state=3  and create_time BETWEEN  ? and  ? GROUP BY group_create_time";
        List<Payment> paymentList = new ArrayList();
        getJdbcTemplate().query(sql, new Object[]{starTime, endTime},  new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                Payment payment = new Payment();
                payment.setStatisticsPrice(rs.getString("math"));
                payment.setGroupCreateTime(rs.getString("group_create_time"));
                paymentList.add(payment);
            }
        });
        return paymentList;
    }

    /**
     * 通过创建时间段统计 订阅数量 （统计的正式订阅）
     * @param starTime 开始时间
     * @param endTime 结束时间
     */
    public List<Subscribe> statisticsSubscribe(String starTime, String endTime) {
        String  sql = "select group_create_time, sum(1)  as math from trc_subscribe  where subscribe_type=2  and create_time BETWEEN  ? and  ? GROUP BY group_create_time";
        List<Subscribe> scribeList = new ArrayList();
        getJdbcTemplate().query(sql, new Object[]{starTime, endTime},  new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                Subscribe scribe = new Subscribe();
                scribe.setNumber(rs.getString("math"));
                scribe.setGroupCreateTime(rs.getString("group_create_time"));
                scribeList.add(scribe);
            }
        });
        return scribeList;
    }
    /**
     * 通过创建时间段统计 浏览数量
     * @param starTime
     */
    public List<Visit> statisticsVisit(String starTime, String endTime) {
        String  sql = "select group_create_time, sum(num)  as math from stat_visit  where  create_time BETWEEN  ? and  ? GROUP BY group_create_time";
        List<Visit> visitList = new ArrayList();
        getJdbcTemplate().query(sql, new Object[]{starTime, endTime},  new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                Visit visit = new Visit();
                visit.setNum(rs.getInt("math"));
                visit.setGroupCreateTime(rs.getString("group_create_time"));
                visitList.add(visit);
            }
        });
        return visitList;
    }

    public JdbcTemplate getJdbcTemplate() {

        return jpaTemplate.getJdbcTemplate();
    }


    public List<MemberEntity> findMemberByLikeTime(String time) {
        QueryCondition queryCondition = QueryBuilders.createQuery(MemberEntity.class)
                .like("groupCreateTime", time)
                .get();
        return jpaTemplate.findList(queryCondition, MemberEntity.class);
    }

    public List<TenantEntity> findTenantByLikeTime(String time) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TenantEntity.class)
                .like("groupCreateTime", time)
                .get();
        return jpaTemplate.findList(queryCondition, TenantEntity.class);
    }

    public List<OrderEntity> findOrderByLikeTime(String time) {
        QueryCondition queryCondition = QueryBuilders.createQuery(OrderEntity.class)
                .like("groupCreateTime", time)
                .get();
        return jpaTemplate.findList(queryCondition, OrderEntity.class);
    }

    public List<PaymentEntity> findPaymentByLikeTime(String time) {
        QueryCondition queryCondition = QueryBuilders.createQuery(PaymentEntity.class)
                .like("groupCreateTime", time)
                .get();
        return jpaTemplate.findList(queryCondition, PaymentEntity.class);
    }

    public List<SubscribeEntity> findSubscribeByLikeTime(String time) {
        QueryCondition queryCondition = QueryBuilders.createQuery(SubscribeEntity.class)
                .like("groupCreateTime", time)
                .get();
        return jpaTemplate.findList(queryCondition, SubscribeEntity.class);
    }
    public List<VisitEntity> findVisitByLikeTime(String time) {
        QueryCondition queryCondition = QueryBuilders.createQuery(VisitEntity.class)
                .like("groupCreateTime", time)
                .get();
        return jpaTemplate.findList(queryCondition, VisitEntity.class);
    }
}
