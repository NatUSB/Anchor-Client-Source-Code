package com.anchorclient.client.util.misc;

import lombok.experimental.UtilityClass;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class ScheduleUtil {

    /**
     * Schedules a task for the specified interval
     * @param r The task
     * @param time The time
     * @param timeUnit The time-unit to use
     */
    public void schedule(Runnable r, int time, TimeUnit timeUnit) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(r, 0, time, timeUnit);
    }

}
