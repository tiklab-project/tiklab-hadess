package io.thoughtware.hadess.repository.dao;

import io.thoughtware.hadess.repository.entity.XpackBackupsEntity;
import io.thoughtware.dal.jpa.JpaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class XpackBackupsDao {

    private static Logger logger = LoggerFactory.getLogger(XpackBackupsDao.class);

    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 更新
     * @param backupsEntity
     */
    public void updateBackups(XpackBackupsEntity backupsEntity){
        jpaTemplate.update(backupsEntity);
    }


    /**
     * findAllBackups
     * @return
     */
    public List<XpackBackupsEntity> findAllBackups() {
        return jpaTemplate.findAll(XpackBackupsEntity.class);
    }


}
