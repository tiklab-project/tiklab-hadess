package io.tiklab.xpack.repository.service;

import io.tiklab.xpack.config.TestConfig;
import io.tiklab.postin.client.mock.JMockit;
import io.tiklab.xpack.repository.model.Storage;
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
public class StorageServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(StorageServiceImplTest.class);

    @Autowired
    StorageService storageService;

    static String id;

    @Test
    public void test01ForSaveStorage() {
        Storage storage = JMockit.mock(Storage.class);

        id = storageService.createStorage(storage);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateStorage(){
        Storage storage = JMockit.mock(Storage.class);
        storage.setId(id);

        storageService.updateStorage(storage);
    }

    @Test
    public void test03ForFindStorage() {
        Storage storage = storageService.findStorage(id);

        assertNotNull(storage);
    }

    @Test
    public void test04ForFindAllStorage() {
        List<Storage> storageList = storageService.findAllStorage();

        assertNotNull(storageList);
    }

    @Test
    public void test05ForDeleteStorage(){
        storageService.deleteStorage(id);
    }
}