package com.tchristofferson.worker.threadworkers.impl;

import com.tchristofferson.worker.Task;
import com.tchristofferson.worker.threadworkers.ThreadWorker;

public class AsyncSingleThreadWorker extends ThreadWorker {

    @Override
    protected void runTask(Task task) {
        task.run();
    }
}
