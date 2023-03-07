package io.tiklab.xpack.library.service;

import io.tiklab.xpack.config.TestConfig;
import io.tiklab.postin.client.mock.JMockit;
import io.tiklab.xpack.library.model.LibraryFile;
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
public class LibraryFileServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(LibraryFileServiceImplTest.class);

    @Autowired
    LibraryFileService libraryFileService;

    static String id;

    @Test
    public void test01ForSaveLibraryFile() {
        LibraryFile libraryFile = JMockit.mock(LibraryFile.class);

        id = libraryFileService.createLibraryFile(libraryFile);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateLibraryFile(){
        LibraryFile libraryFile = JMockit.mock(LibraryFile.class);
        libraryFile.setId(id);

        libraryFileService.updateLibraryFile(libraryFile);
    }

    @Test
    public void test03ForFindLibraryFile() {
        LibraryFile libraryFile = libraryFileService.findLibraryFile(id);

        assertNotNull(libraryFile);
    }

    @Test
    public void test04ForFindAllLibraryFile() {
        List<LibraryFile> libraryFileList = libraryFileService.findAllLibraryFile();

        assertNotNull(libraryFileList);
    }

    @Test
    public void test05ForDeleteLibraryFile(){
        libraryFileService.deleteLibraryFile(id);
    }
}