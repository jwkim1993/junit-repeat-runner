package com.naver.test;

import com.naver.annotation.Repeat;
import com.naver.common.RepeatRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@RunWith(RepeatRunner.class)
public class MyTestClass {

    public void testMyCodeOnce() {
        Calculator calc = new Calculator();

        Random random = new Random();
        int a = random.nextInt(100);
        int b = random.nextInt(100);

        Assert.assertEquals(calc.calcTwoNumbers(a, b), a+b);
    }

    @Test
    @Repeat(5)
    public void testMyCode5Times() {
        System.out.println("Test my code 5 times...");
        testMyCodeOnce();
    }

    @Test
    @Repeat(10)
    public void testMyCode10Times() {
        System.out.println("Test my code 10 times...");
        testMyCodeOnce();
    }

}
