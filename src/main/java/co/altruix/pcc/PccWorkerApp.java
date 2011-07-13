/**
 * This file is part of Project Control Center (PCC).
 * 
 * PCC (Project Control Center) project is intellectual property of 
 * Dmitri Anatol'evich Pisarenko.
 * 
 * Copyright 2010, 2011 Dmitri Anatol'evich Pisarenko
 * All rights reserved
 *
 **/

package co.altruix.pcc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.channels.PccChannel;
import co.altruix.pcc.api.dispatcher.Dispatcher;

public class PccWorkerApp {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PccWorkerApp.class);

    void run() {
        Dispatcher processor = null;
        PccChannel webToWorkerQueueChannel = getQueueChannel("PCC.WEB.WORKER");

        processor.addChannel(webToWorkerQueueChannel);

        while (true) {
            try {
                processor.run();
            } catch (final PccException exception) {
                LOGGER.error("", exception);
            }
            
            try {
                Thread.sleep(500);
            } catch (final InterruptedException exception) {
                LOGGER.error("", exception);
            }
        }
    }

    private PccChannel getQueueChannel(String string) {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
