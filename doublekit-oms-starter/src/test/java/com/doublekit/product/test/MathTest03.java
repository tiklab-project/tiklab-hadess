package com.doublekit.product.test;

import com.doublekit.product.config.ProductServerAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = {ProductServerAutoConfiguration.class})
@RunWith(SpringRunner.class)
public class MathTest03 {
    @Autowired
    MathTest mathTest;
    @Test
    public void add(){
        String integer = mathTest.testAdd(1, 3);
        assertEquals("4",integer);
    }


   /* @Test
    public void add02(){
        String integer = mathTest.testAdd(1, 3);
        assertEquals("3",integer);
    }*/
}
