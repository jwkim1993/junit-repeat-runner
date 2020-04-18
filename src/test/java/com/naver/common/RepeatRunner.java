package com.naver.common;

import com.naver.annotation.Repeat;

import org.junit.Assert;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class RepeatRunner extends BlockJUnit4ClassRunner {

    public RepeatRunner(Class<?> klass) throws InitializationError {
        super(klass);
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
                    int count = getCount(method);
                    for(int i = 0 ; i < count ; ++i)
                        methodBlock(method).evaluate();
                }
            };
            runLeaf(statement, desc, notifier);
        }
    }

    protected int getCount(FrameworkMethod child) {
        int count = child.getAnnotation(Repeat.class) == null ? 1 : child.getAnnotation(Repeat.class).value();
        Assert.assertTrue("Parameter of Repeat(N) should be greater than or equal to 1", count >= 1);

        return count;
    }

}
