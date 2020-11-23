package com.tchristofferson.worker.threadworkers;

import com.tchristofferson.worker.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ThreadWorker {

    protected final AtomicBoolean isShutdown = new AtomicBoolean(false);
    protected final BlockingQueue<Task> work = new PriorityBlockingQueue<>();
    protected final Thread workerThread = new Thread(this::doTasks);

    public ThreadWorker() {
        workerThread.start();
    }

    /**
     * Showdown this thread worker. The thread worker will no longer be able to accept new tasks.<br>
     * @param waitForWorkerThreadCompletion If {@code true}, the thread calling this method will be blocked until all tasks complete
     * @throws InterruptedException If waiting for worker thread(s) to complete and a thread is interrupted
     */
    public void shutdown(boolean waitForWorkerThreadCompletion) throws InterruptedException {
        synchronized (isShutdown) {
            isShutdown.set(true);
            workerThread.interrupt();
        }

        if (waitForWorkerThreadCompletion)
            workerThread.join();
    }

    /**
     * Run a task on a different thread
     * @param task The task to be ran
     */
    public void run(Task task) {
        synchronized (isShutdown) {
            if (isShutdown.get())
                throw new IllegalStateException("Cannot run task when the worker is shutdown!");

            synchronized (workerThread) {
                if (workerThread.getState() == Thread.State.WAITING)
                    workerThread.interrupt();

                synchronized (work) {
                    task.setScheduled(System.nanoTime());
                    work.add(task);
                }
            }
        }
    }

    protected abstract void runTask(Task task);

    private void doTasks() {
        Task task;

        while (true) {
            synchronized (isShutdown) {
                synchronized (work) {
                    if (isShutdown.get() && work.isEmpty())
                        return;
                }
            }

            synchronized (work) {
                try {
                    task = work.take();
                } catch (InterruptedException e) {
                    continue;
                }
            }

            runTask(task);
        }
    }
}
