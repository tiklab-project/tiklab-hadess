package net.tiklab.xpack.repository.service;

import net.tiklab.postin.client.mock.JMockit;
import net.tiklab.xpack.repository.model.RepositoryClusterCfg;
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
public class RepositoryClusterCfgServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(RepositoryClusterCfgServiceImplTest.class);

    @Autowired
    RepositoryClusterCfgService repositoryClusterCfgService;

    static String id;

    @Test
    public void test01ForSaveRepositoryClusterCfg() {
        RepositoryClusterCfg repositoryClusterCfg = JMockit.mock(RepositoryClusterCfg.class);

        id = repositoryClusterCfgService.createRepositoryClusterCfg(repositoryClusterCfg);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateRepositoryClusterCfg(){
        RepositoryClusterCfg repositoryClusterCfg = JMockit.mock(RepositoryClusterCfg.class);
        repositoryClusterCfg.setId(id);

        repositoryClusterCfgService.updateRepositoryClusterCfg(repositoryClusterCfg);
    }

    @Test
    public void test03ForFindRepositoryClusterCfg() {
        RepositoryClusterCfg repositoryClusterCfg = repositoryClusterCfgService.findRepositoryClusterCfg(id);

        assertNotNull(repositoryClusterCfg);
    }

    @Test
    public void test04ForFindAllRepositoryClusterCfg() {
        List<RepositoryClusterCfg> repositoryClusterCfgList = repositoryClusterCfgService.findAllRepositoryClusterCfg();

        assertNotNull(repositoryClusterCfgList);
    }

    @Test
    public void test05ForDeleteRepositoryClusterCfg(){
        repositoryClusterCfgService.deleteRepositoryClusterCfg(id);
    }
}