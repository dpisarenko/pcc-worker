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

package co.altruix.dispatcher;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.altruix.commons.api.di.InjectorFactory;
import ru.altruix.commons.api.di.PccException;

import co.altruix.pcc.api.dispatcher.Dispatcher;
import co.altruix.pcc.api.dispatcher.DispatcherFactory;

import com.google.inject.Injector;

public class TestDefaultDispatcher {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TestDefaultDispatcher.class);

    @Test
    public void testActiveMQObjectMessage() {
        final InjectorFactory injectorFactory =
                new MockInjectorFactory(new MockInjectorModule());
        final Injector injector = injectorFactory.createInjector();

        final DispatcherFactory factory =
                injector.getInstance(DispatcherFactory.class);
        final Dispatcher objectUnderTest = factory.create();

        
        final MockChannel channel = new MockChannel();
        
        objectUnderTest.addIncomingChannel(channel);
        
        objectUnderTest.setInjector(injector);
        objectUnderTest.setTesterLogFilePath(new File("src/test/co/altruix/dispatcher/notification.log.txt"));
        
        try {
            objectUnderTest.run();
        } catch (final PccException exception) {
            LOGGER.error("", exception);
            Assert.fail(exception.getMessage());
        }
    }
}
