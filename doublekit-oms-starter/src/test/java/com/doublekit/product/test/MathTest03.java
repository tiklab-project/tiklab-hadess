package com.doublekit.product.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class MathTest03 {

    @Test
    public void add(){
        int result=1+3;
        assertEquals(4,result);
    }

    @Test
    public void add02(){
        int result=1+3;
        assertEquals(5,result);
    }

   /* @Test
    public void add02(){
        String integer = mathTest.testAdd(1, 3);
        assertEquals("3",integer);
    }*/
}
