package io.tiklab.xpack.alter.dao;

import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Repository
public class AlterSqlDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public void updateId(){
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        String sql="SHOW TABLES";
        List<String> stringList = jdbcTemplate.queryForList(sql, String.class);

        for (String tableName:stringList ){
            if (tableName.startsWith("pack")){
                exeSql(tableName,"id");
                if (("pack_repository").equals(tableName)){
                    exeSql(tableName,"storage_id");
                }
                if (("pack_repository_maven").equals(tableName)){
                    exeSql(tableName,"repository_id");
                }
                if (("pack_repository_remote_proxy").equals(tableName)){
                    exeSql(tableName,"repository_id");
                }
                if (("pack_repository_group_items").equals(tableName)){
                    exeSql(tableName,"repository_group_id");
                    exeSql(tableName,"repository_id");
                }
                if (("pack_repository_cluster_cfg").equals(tableName)){
                    exeSql(tableName,"repository_id");
                    exeSql(tableName,"create_user");
                }
                if (("pack_library").equals(tableName)){
                    exeSql(tableName,"repository_id");
                }
                if (("pack_library_version").equals(tableName)){
                    exeSql(tableName,"library_id");
                    exeSql(tableName,"repository_id");
                }
                if (("pack_library_file").equals(tableName)){
                    exeSql(tableName,"library_id");
                    exeSql(tableName,"library_version_id");
                    exeSql(tableName,"repository_id");
                }
                if (("pack_library_maven").equals(tableName)){
                    exeSql(tableName,"library_id");
                }
                if (("pack_pull_info").equals(tableName)){
                    exeSql(tableName,"library_id");
                    exeSql(tableName,"library_version_id");
                    exeSql(tableName,"user_id");
                }
            }

            if (tableName.startsWith("pcs")){
                exeSql(tableName,"id");
                if (("pcs_mec_message_dispatch_item").equals(tableName)){
                    exeSql(tableName,"message_id");
                    exeSql(tableName,"message_type_id");
                }
                if (("pcs_mec_message_notice").equals(tableName)){
                    exeSql(tableName,"message_type_id");
                }
                if (("pcs_mec_message_notice_connect_orga").equals(tableName)){
                    exeSql(tableName,"orga_id");
                }
                if (("pcs_mec_message_notice_connect_role").equals(tableName)){
                    exeSql(tableName,"role_id");
                }
                if (("pcs_mec_message_notice_connect_user").equals(tableName)){
                    exeSql(tableName,"user_id");
                }
                if (("pcs_mec_message_notice_connect_usergroup").equals(tableName)){
                    exeSql(tableName,"usergroup_id");
                }
                if (("pcs_mec_message_receiver").equals(tableName)){
                    exeSql(tableName,"message_id");
                }
                if (("pcs_mec_message_receiver").equals(tableName)){
                    exeSql(tableName,"message_id");
                }
                if (("pcs_mec_message_template").equals(tableName)){
                    exeSql(tableName,"msg_type_id");
                    exeSql(tableName,"msg_send_type_id");
                }
                if (("pcs_prc_dm_role").equals(tableName)){
                    exeSql(tableName,"domain_id");
                    exeSql(tableName,"role_id");
                }
                if (("pcs_prc_dm_role_user").equals(tableName)){
                    exeSql(tableName,"dmRole_id");
                    exeSql(tableName,"domain_id");
                    exeSql(tableName,"user_id");
                }
                if (("pcs_prc_dm_role_user_group").equals(tableName)){
                    exeSql(tableName,"dmRole_id");
                    exeSql(tableName,"domain_id");
                    exeSql(tableName,"group_id");
                }
                if (("pcs_prc_dm_role_vuser").equals(tableName)){
                    exeSql(tableName,"dmRole_id");
                    exeSql(tableName,"domain_id");
                    exeSql(tableName,"vuser_id");
                }
                if (("pcs_prc_function").equals(tableName)){
                    exeSql(tableName,"parent_function_id");
                }
                if (("pcs_prc_product_authorization").equals(tableName)){
                    exeSql(tableName,"user_id");
                }
                if (("pcs_prc_role_function").equals(tableName)){
                    exeSql(tableName,"role_id");
                    exeSql(tableName,"function_id");
                }
                if (("pcs_prc_role_user").equals(tableName)){
                    exeSql(tableName,"role_id");
                    exeSql(tableName,"user_id");
                }
                if (("pcs_prc_role_user_group").equals(tableName)){
                    exeSql(tableName,"role_id");
                    exeSql(tableName,"group_id");
                }
                if (("pcs_prc_role_vuser").equals(tableName)){
                    exeSql(tableName,"role_id");
                    exeSql(tableName,"group_id");
                }
                if (("pcs_todo_task").equals(tableName)){
                    exeSql(tableName,"create_user");
                    exeSql(tableName,"assign_user");
                }
                if (("pcs_ucc_dm_user").equals(tableName)){
                    exeSql(tableName,"domain_id");
                    exeSql(tableName,"user_id");
                }
                if (("pcs_ucc_dm_vuser").equals(tableName)){
                    exeSql(tableName,"domain_id");
                    exeSql(tableName,"vuser_id");
                }
                if (("pcs_ucc_orga").equals(tableName)){
                    exeSql(tableName,"parent_orga_id");
                }
                if (("pcs_ucc_orga_user").equals(tableName)){
                    exeSql(tableName,"user_id");
                    exeSql(tableName,"orga_id");
                }
                if (("pcs_ucc_usergroup_user").equals(tableName)){
                    exeSql(tableName,"user_id");
                    exeSql(tableName,"group_id");
                }
                if (("pcs_ucc_usergroup_user").equals(tableName)){
                    exeSql(tableName,"user_id");
                    exeSql(tableName,"group_id");
                }
            }

        }
    }

    public void exeSql(String tableName,String fields ){
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable(){
            @Override
            public void run() {
                JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
                String findSql="SELECT "+ fields+ " FROM "+tableName;
                List<String> stringList = jdbcTemplate.queryForList(findSql, String.class);
                for (String field:stringList){
                    if (StringUtils.isNotEmpty(field)&&field.length()>12){

                        String newId = field.substring(0, 12);
                        String updateSql="UPDATE "+tableName+" SET "+fields+"='"+newId+"' WHERE " +fields+"= '"+field+"'";
                        System.out.println("updateSql:"+updateSql);
                        jdbcTemplate.execute(updateSql);
                    }
                }
                System.out.println("表名"+tableName);
                String sql="ALTER TABLE "+tableName+" MODIFY "+fields+ " varchar(12)";
                jdbcTemplate.execute(sql);
            }
        });

    }

}
