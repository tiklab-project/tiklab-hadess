package net.tiklab.xpack.repository.service;

import net.tiklab.postin.client.mock.JMockit;
import net.tiklab.xpack.repository.model.RepositoryGroup;
import net.tiklab.xpack.config.TestConfig;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Transactional
@Rollback(false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepositoryGroupServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(RepositoryGroupServiceImplTest.class);

    @Autowired
    RepositoryGroupService repositoryGroupService;

    static String id;

    @Test
    public void test01ForSaveRepositoryGroup() {
        RepositoryGroup repositoryGroup = JMockit.mock(RepositoryGroup.class);

        id = repositoryGroupService.createRepositoryGroup(repositoryGroup);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateRepositoryGroup(){
        RepositoryGroup repositoryGroup = JMockit.mock(RepositoryGroup.class);
        repositoryGroup.setId(id);

        repositoryGroupService.updateRepositoryGroup(repositoryGroup);
    }

    @Test
    public void test03ForFindRepositoryGroup() {
        RepositoryGroup repositoryGroup = repositoryGroupService.findRepositoryGroup(id);

        assertNotNull(repositoryGroup);
    }

    @Test
    public void test04ForFindAllRepositoryGroup() {
        List<RepositoryGroup> repositoryGroupList = repositoryGroupService.findAllRepositoryGroup();

        assertNotNull(repositoryGroupList);
    }

    @Test
    public void test05ForDeleteRepositoryGroup(){
        repositoryGroupService.deleteRepositoryGroup(id);
    }
}