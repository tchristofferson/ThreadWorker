package com.tchristofferson.worker.threadworkers.impl;

import com.tchristofferson.worker.Task;
import com.tchristofferson.worker.threadworkers.ThreadWorker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncCachedThreadWorker extends ThreadWorker {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    protected void runTask(Task task) {
        executorService.submit(task);
    }

    @Override
    public void shutdown(boolean waitForWorkerThreadCompletion) throws InterruptedException {
        executorService.shutdown();
        super.shutdown(waitForWorkerThreadCompletion);
    }
}
