package com.doublekit.product.test;

import org.springframework.stereotype.Service;

@Service
public class Test01 implements MathTest {
    @Override
    public String testAdd(int a, int b) {
        return String.valueOf(a+b);
    }
}
