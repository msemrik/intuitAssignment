package com.intuit.requesthandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledRefresh {

    @Autowired
    ApplicationRequestHandler applicationRequestHandler;

    @Scheduled(fixedRateString = "#{${automaticRefreshInMinutes} * 60 * 1000}")
    public void scheduledRefresh () throws Exception {
        applicationRequestHandler.refreshData();
    }
}
