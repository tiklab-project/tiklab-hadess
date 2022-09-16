package  net.tiklab.oms.role.service;

import  net.tiklab.oms.role.model.RoleUser;
import  net.tiklab.oms.config.TestConfig;
import  net.tiklab.postin.client.mock.JMockit;
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
public class RoleUserServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(RoleUserServiceImplTest.class);

    @Autowired
    RoleUserService roleUserService;

    static String id;

    @Test
    public void test01ForSaveRoleUser() {
        RoleUser roleUser = JMockit.mock(RoleUser.class);

        id = roleUserService.createRoleUser(roleUser);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateRoleUser(){
        RoleUser roleUser = JMockit.mock(RoleUser.class);
        roleUser.setId(id);

        roleUserService.updateRoleUser(roleUser);
    }

    @Test
    public void test03ForFindRoleUser() {
        RoleUser roleUser = roleUserService.findRoleUser(id);

        assertNotNull(roleUser);
    }

    @Test
    public void test04ForFindAllRoleUser() {
        List<RoleUser> roleUserList = roleUserService.findAllRoleUser();

        assertNotNull(roleUserList);
    }

    @Test
    public void test05ForDeleteRoleUser(){
        roleUserService.deleteRoleUser(id);
    }
}