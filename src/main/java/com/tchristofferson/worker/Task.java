package com.tchristofferson.worker;

public abstract class Task implements Runnable, Comparable<Task> {

    private volatile Long scheduled;

    public synchronized void setScheduled(long l) {
        if (scheduled != null)
            throw new IllegalStateException("Scheduled time already set!");

        this.scheduled = l;
    }

    @Override
    public synchronized int compareTo(Task o) {
        if (o == null)
            throw new NullPointerException("Task cannot be null!");

        if (scheduled == null)
            throw new IllegalStateException("Scheduled time hasn't been set!");

        return scheduled.compareTo(o.scheduled);
    }
}
