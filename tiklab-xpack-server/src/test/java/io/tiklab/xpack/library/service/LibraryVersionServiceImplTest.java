package io.tiklab.xpack.library.service;

import io.tiklab.xpack.config.TestConfig;
import io.tiklab.postin.client.mock.JMockit;
import io.tiklab.xpack.library.model.LibraryVersion;
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
public class LibraryVersionServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(LibraryVersionServiceImplTest.class);

    @Autowired
    LibraryVersionService libraryVersionService;

    static String id;

    @Test
    public void test01ForSaveLibraryVersion() {
        LibraryVersion libraryVersion = JMockit.mock(LibraryVersion.class);

        id = libraryVersionService.createLibraryVersion(libraryVersion);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateLibraryVersion(){
        LibraryVersion libraryVersion = JMockit.mock(LibraryVersion.class);
        libraryVersion.setId(id);

        libraryVersionService.updateLibraryVersion(libraryVersion);
    }

    @Test
    public void test03ForFindLibraryVersion() {
        LibraryVersion libraryVersion = libraryVersionService.findLibraryVersion(id);

        assertNotNull(libraryVersion);
    }

    @Test
    public void test04ForFindAllLibraryVersion() {
        List<LibraryVersion> libraryVersionList = libraryVersionService.findAllLibraryVersion();

        assertNotNull(libraryVersionList);
    }

    @Test
    public void test05ForDeleteLibraryVersion(){
        libraryVersionService.deleteLibraryVersion(id);
    }
}