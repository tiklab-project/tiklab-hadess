package  net.tiklab.oms.role.codegen;

import  net.tiklab.codegen.CodeGeneratorTemplate;
import  net.tiklab.codegen.config.CodeGeneratorConfig;
import  net.tiklab.codegen.config.ProjectGeneratorConfig;
import  net.tiklab.oms.role.entity.RoleEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProjectGeneratorConfig.class)
public class RoleCodeGeneratorTest extends CodeGeneratorTemplate {

    @Autowired
    ProjectGeneratorConfig projectGeneratorConfig;

    @Override
    protected CodeGeneratorConfig getCodeGeneratorConfig() {
        CodeGeneratorConfig config = new CodeGeneratorConfig();
        config.setProjectGeneratorConfig(projectGeneratorConfig);
        config.setEntity(RoleEntity.class);
        config.setPkg(" net.tiklab.oms.role");
        config.setModel("Role");
        return config;
    }

    @Test
    @Override
    public void generateForAll() {
        super.generateForAll();
    }
}