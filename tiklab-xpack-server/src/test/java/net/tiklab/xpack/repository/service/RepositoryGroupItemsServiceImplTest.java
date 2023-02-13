package net.tiklab.xpack.repository.service;

import net.tiklab.postin.client.mock.JMockit;
import net.tiklab.xpack.repository.model.RepositoryGroupItems;
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
public class RepositoryGroupItemsServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(RepositoryGroupItemsServiceImplTest.class);

    @Autowired
    RepositoryGroupItemsService repositoryGroupItemsService;

    static String id;

    @Test
    public void test01ForSaveRepositoryGroupItems() {
        RepositoryGroupItems repositoryGroupItems = JMockit.mock(RepositoryGroupItems.class);

        id = repositoryGroupItemsService.createRepositoryGroupItems(repositoryGroupItems);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateRepositoryGroupItems(){
        RepositoryGroupItems repositoryGroupItems = JMockit.mock(RepositoryGroupItems.class);
        repositoryGroupItems.setId(id);

        repositoryGroupItemsService.updateRepositoryGroupItems(repositoryGroupItems);
    }

    @Test
    public void test03ForFindRepositoryGroupItems() {
        RepositoryGroupItems repositoryGroupItems = repositoryGroupItemsService.findRepositoryGroupItems(id);

        assertNotNull(repositoryGroupItems);
    }

    @Test
    public void test04ForFindAllRepositoryGroupItems() {
        List<RepositoryGroupItems> repositoryGroupItemsList = repositoryGroupItemsService.findAllRepositoryGroupItems();

        assertNotNull(repositoryGroupItemsList);
    }

    @Test
    public void test05ForDeleteRepositoryGroupItems(){
        repositoryGroupItemsService.deleteRepositoryGroupItems(id);
    }
}