package com.naver.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class MyTestClass {

    @Test
    public void testMyCodeOnce() {
        Calculator calc = new Calculator();

        Random random = new Random();
        int a = random.nextInt(100);
        int b = random.nextInt(100);

        Assert.assertEquals(calc.calcTwoNumbers(a, b), a+b);
    }

}
