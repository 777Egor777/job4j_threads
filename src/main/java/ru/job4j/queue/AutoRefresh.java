package ru.job4j.queue;

import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 05.01.2021
 */
public final class AutoRefresh {
    private final int period;
    private final Refresh refresh;
    private Scheduler scheduler;
    public AutoRefresh(int period, Refresh refresh) {
        this.period = period;
        this.refresh = refresh;
        init();
    }

    private void init() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap jdm = new JobDataMap();
            jdm.put("refresh", refresh);
            JobDetail job = JobBuilder.newJob(RefreshJob.class).setJobData(jdm).build();
            SimpleScheduleBuilder times = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(period)
                    .repeatForever();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void shutDown() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static class RefreshJob implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            Refresh refresh = (Refresh) jobExecutionContext.getJobDetail().getJobDataMap()
                    .get("refresh");
            refresh.refresh();
        }
    }
}
