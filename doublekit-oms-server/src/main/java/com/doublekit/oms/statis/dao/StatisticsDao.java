package com.doublekit.oms.statis.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.member.member.model.Member;
import com.doublekit.subscribe.order.model.Order;
import com.doublekit.subscribe.subscribe.model.Subscribe;
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

    public JdbcTemplate getJdbcTemplate() {

        return jpaTemplate.getJdbcTemplate();
    }
}
