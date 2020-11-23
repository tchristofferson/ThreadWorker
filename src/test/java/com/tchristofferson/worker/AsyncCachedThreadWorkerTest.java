package com.tchristofferson.worker;

import com.tchristofferson.worker.threadworkers.ThreadWorker;
import com.tchristofferson.worker.threadworkers.impl.AsyncCachedThreadWorker;
import org.junit.jupiter.api.*;

public class AsyncCachedThreadWorkerTest {

    private static final ThreadWorker worker = new AsyncCachedThreadWorker();

    @AfterAll
    @Timeout(3)
    public static void shutdown() {
        try {
            ThreadWorkerTestUtil.shutdown(worker);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RepeatedTest(10)
    @Order(1)
    @Timeout(3)
    public void testSynchronization() {
        ThreadWorkerTestUtil.testSynchronization(worker);
    }

    @RepeatedTest(10)
    @Order(2)
    @Timeout(3)
    public void testSynchronizationWithDelay() throws InterruptedException {
        ThreadWorkerTestUtil.testSynchronizationWithDelay(worker, 10);
    }
}
