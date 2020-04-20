package com.naver.common;

import org.junit.Assert;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class RepeatRunner extends BlockJUnit4ClassRunner {

    public RepeatRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Repeat {
        int value() default 1;
    }

    @Override
    protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
        Description desc = describeChild(method);
        if(isIgnored(method)) {
            notifier.fireTestIgnored(desc);
        } else {
            Statement statement = new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    methodBlock(method).evaluate();
                }
            };
            int count = getCount(method);
            for(int i = 0 ; i < count ; ++i)
                runLeaf(statement, desc, notifier);
        }
    }

    protected int getCount(FrameworkMethod child) {
        int count = child.getAnnotation(Repeat.class) == null ? 1 : child.getAnnotation(Repeat.class).value();
        Assert.assertTrue("Parameter of Repeat(N) should be greater than or equal to 1", count >= 1);

        return count;
    }

}
