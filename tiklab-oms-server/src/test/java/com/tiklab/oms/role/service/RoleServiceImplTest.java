package com.tiklab.oms.role.service;

import com.tiklab.oms.role.model.Role;
import com.tiklab.oms.config.TestConfig;
import com.tiklab.postin.client.mock.JMockit;
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
public class RoleServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(RoleServiceImplTest.class);

    @Autowired
    RoleService roleService;

    static String id;

    @Test
    public void test01ForSaveRole() {
        Role role = JMockit.mock(Role.class);

        id = roleService.createRole(role);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateRole(){
        Role role = JMockit.mock(Role.class);
        role.setId(id);

        roleService.updateRole(role);
    }

    @Test
    public void test03ForFindRole() {
        Role role = roleService.findRole(id);

        assertNotNull(role);
    }

    @Test
    public void test04ForFindAllRole() {
        List<Role> roleList = roleService.findAllRole();

        assertNotNull(roleList);
    }

    @Test
    public void test05ForDeleteRole(){
        roleService.deleteRole(id);
    }
}