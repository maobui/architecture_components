package com.me.bui.architecturecomponents.util;

import android.arch.core.executor.testing.CountingTaskExecutorRule;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;

import org.junit.runner.Description;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by mao.bui on 9/11/2018.
 */
public class TaskExecutorWithIdlingResourceRule extends CountingTaskExecutorRule {
    private CopyOnWriteArrayList<IdlingResource.ResourceCallback> callbacks =
            new CopyOnWriteArrayList<>();
    @Override
    protected void starting(Description description) {
        Espresso.registerIdlingResources(new IdlingResource() {
            @Override
            public String getName() {
                return "architecture components idling resource";
            }
            @Override
            public boolean isIdleNow() {
                return TaskExecutorWithIdlingResourceRule.this.isIdle();
            }
            @Override
            public void registerIdleTransitionCallback(ResourceCallback callback) {
                callbacks.add(callback);
            }
        });
        super.starting(description);
    }
    @Override
    protected void onIdle() {
        super.onIdle();
        for (IdlingResource.ResourceCallback callback : callbacks) {
            callback.onTransitionToIdle();
        }
    }
}
