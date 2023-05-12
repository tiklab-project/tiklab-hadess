package io.tiklab.xpack.alter.service;

import io.tiklab.xpack.alter.dao.AlterSqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlterSqlServiceImpl implements AlterSqlService{

    @Autowired
    AlterSqlDao alterSqlDao;

    @Override
    public void updateId() {
        alterSqlDao.updateId();
    }
}
