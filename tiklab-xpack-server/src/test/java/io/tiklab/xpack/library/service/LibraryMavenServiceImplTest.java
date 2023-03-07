package io.tiklab.xpack.library.service;

import io.tiklab.xpack.config.TestConfig;
import io.tiklab.postin.client.mock.JMockit;
import io.tiklab.xpack.library.model.LibraryMaven;
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
public class LibraryMavenServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(LibraryMavenServiceImplTest.class);

    @Autowired
    LibraryMavenService libraryMavenService;

    static String id;

    @Test
    public void test01ForSaveLibraryMaven() {
        LibraryMaven libraryMaven = JMockit.mock(LibraryMaven.class);

        id = libraryMavenService.createLibraryMaven(libraryMaven);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateLibraryMaven(){
        LibraryMaven libraryMaven = JMockit.mock(LibraryMaven.class);
        libraryMaven.setId(id);

        libraryMavenService.updateLibraryMaven(libraryMaven);
    }

    @Test
    public void test03ForFindLibraryMaven() {
        LibraryMaven libraryMaven = libraryMavenService.findLibraryMaven(id);

        assertNotNull(libraryMaven);
    }

    @Test
    public void test04ForFindAllLibraryMaven() {
        List<LibraryMaven> libraryMavenList = libraryMavenService.findAllLibraryMaven();

        assertNotNull(libraryMavenList);
    }

    @Test
    public void test05ForDeleteLibraryMaven(){
        libraryMavenService.deleteLibraryMaven(id);
    }
}