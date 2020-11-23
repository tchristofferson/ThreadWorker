package com.tchristofferson.worker;

import com.tchristofferson.worker.threadworkers.ThreadWorker;

public class ThreadWorkerTestUtil {

    public static void testSynchronization(ThreadWorker worker) {
        worker.run(new Task() {
            @Override
            public void run() {
                //Work here
            }
        });
    }

    public static void testSynchronizationWithDelay(ThreadWorker worker, long delay) throws InterruptedException {
        testSynchronization(worker);
        Thread.sleep(delay);
    }

    public static void shutdown(ThreadWorker worker) throws InterruptedException {
        worker.shutdown(true);
    }
}
