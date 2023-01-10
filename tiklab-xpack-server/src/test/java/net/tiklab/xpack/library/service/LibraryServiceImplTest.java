package net.tiklab.xpack.library.service;

import net.tiklab.postin.client.mock.JMockit;
import net.tiklab.xpack.library.model.Library;
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
public class LibraryServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(LibraryServiceImplTest.class);

    @Autowired
    LibraryService libraryService;

    static String id;

    @Test
    public void test01ForSaveLibrary() {
        Library library = JMockit.mock(Library.class);

        id = libraryService.createLibrary(library);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateLibrary(){
        Library library = JMockit.mock(Library.class);
        library.setId(id);

        libraryService.updateLibrary(library);
    }

    @Test
    public void test03ForFindLibrary() {
        Library library = libraryService.findLibrary(id);

        assertNotNull(library);
    }

    @Test
    public void test04ForFindAllLibrary() {
        List<Library> libraryList = libraryService.findAllLibrary();

        assertNotNull(libraryList);
    }

    @Test
    public void test05ForDeleteLibrary(){
        libraryService.deleteLibrary(id);
    }
}