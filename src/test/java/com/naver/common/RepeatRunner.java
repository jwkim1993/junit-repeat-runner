package com.naver.common;

import com.naver.annotation.Repeat;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RepeatRunner extends Runner {

    private Class testClass;
    public RepeatRunner(Class testClass) {
        super();
        this.testClass = testClass;
    }

    @Override
    public Description getDescription() {
        return Description.createTestDescription(this.testClass,
                "This class is extended class from Runner. \n" +
                      "This helps test functions running N times with @Repeat(N) annotations");
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            Object testObject = testClass.newInstance();
            for(Method m : testClass.getMethods()) {
                if(!m.isAnnotationPresent(Test.class))
                    continue;
                int N = !m.isAnnotationPresent(Repeat.class) ? 1 : m.getAnnotation(Repeat.class).value();
                Assert.assertTrue("Parameter of Repeat(N) should be greater than or equal to 1", N >= 1);

                for(int i = 0 ; i < N ; ++i) {
                    notifier.fireTestStarted(Description.createTestDescription(
                            testClass, m.getName()));
                    m.invoke(testObject);
                    notifier.fireTestFinished(Description.createTestDescription(
                            testClass, m.getName()));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
