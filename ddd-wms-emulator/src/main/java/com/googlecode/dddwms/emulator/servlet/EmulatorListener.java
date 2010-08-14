package com.googlecode.dddwms.emulator.servlet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.dddwms.emulator.Emulator;

public class EmulatorListener implements ServletContextListener {
    private static final Logger log = LoggerFactory
            .getLogger(EmulatorListener.class);
    private ScheduledExecutorService emulatorService = Executors
            .newSingleThreadScheduledExecutor();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.debug("EmulatorManager initialize start");
        String targetUrl = sce.getServletContext()
                .getInitParameter("targetUrl");
        emulatorService.scheduleWithFixedDelay(new Emulator(targetUrl), 1, 1,
                TimeUnit.SECONDS);
        log.debug("EmulatorManager initialize end");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        emulatorService.shutdown();
    }
}
