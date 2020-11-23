package com.tchristofferson.worker.threadworkers.impl;

import com.tchristofferson.worker.Task;
import com.tchristofferson.worker.threadworkers.ThreadWorker;

public class AsyncSingleThreadWorker extends ThreadWorker {

    /**
     * Calls {@link Task#run()}
     * @param task The task to be run
     */
    @Override
    protected void runTask(Task task) {
        task.run();
    }
}
