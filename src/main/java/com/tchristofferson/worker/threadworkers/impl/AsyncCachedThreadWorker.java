package com.tchristofferson.worker.threadworkers.impl;

import com.tchristofferson.worker.Task;
import com.tchristofferson.worker.threadworkers.ThreadWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsyncCachedThreadWorker extends ThreadWorker {

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final List<Future<?>> pendingTasks = new ArrayList<>();

    /**
     * Submit a task the the cached thread pool executor belonging to this thread worker
     * @param task The task to be ran
     */
    @Override
    protected void runTask(Task task) {
        Future<?> future = executorService.submit(task);

        synchronized (pendingTasks) {
            pendingTasks.add(future);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shutdown(boolean waitForWorkerThreadCompletion) throws InterruptedException {
        super.shutdown(waitForWorkerThreadCompletion);
        executorService.shutdown();

        if (waitForWorkerThreadCompletion) {
            synchronized (pendingTasks) {
                for (Future<?> pendingTask : pendingTasks) {
                    pendingTask.wait();
                }
            }
        }
    }
}
