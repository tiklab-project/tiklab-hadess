package net.tiklab.xpack.repository.service;

import net.tiklab.postin.client.mock.JMockit;
import net.tiklab.xpack.repository.model.RepositoryRemoteProxy;
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
public class RepositoryRemoteProxyServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(RepositoryRemoteProxyServiceImplTest.class);

    @Autowired
    RepositoryRemoteProxyService repositoryRemoteProxyService;

    static String id;

    @Test
    public void test01ForSaveRepositoryRemoteProxy() {
        RepositoryRemoteProxy repositoryRemoteProxy = JMockit.mock(RepositoryRemoteProxy.class);

        id = repositoryRemoteProxyService.createRepositoryRemoteProxy(repositoryRemoteProxy);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateRepositoryRemoteProxy(){
        RepositoryRemoteProxy repositoryRemoteProxy = JMockit.mock(RepositoryRemoteProxy.class);
        repositoryRemoteProxy.setId(id);

        repositoryRemoteProxyService.updateRepositoryRemoteProxy(repositoryRemoteProxy);
    }

    @Test
    public void test03ForFindRepositoryRemoteProxy() {
        RepositoryRemoteProxy repositoryRemoteProxy = repositoryRemoteProxyService.findRepositoryRemoteProxy(id);

        assertNotNull(repositoryRemoteProxy);
    }

    @Test
    public void test04ForFindAllRepositoryRemoteProxy() {
        List<RepositoryRemoteProxy> repositoryRemoteProxyList = repositoryRemoteProxyService.findAllRepositoryRemoteProxy();

        assertNotNull(repositoryRemoteProxyList);
    }

    @Test
    public void test05ForDeleteRepositoryRemoteProxy(){
        repositoryRemoteProxyService.deleteRepositoryRemoteProxy(id);
    }
}