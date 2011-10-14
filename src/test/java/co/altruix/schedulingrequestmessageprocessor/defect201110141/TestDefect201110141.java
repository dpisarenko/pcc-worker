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

package co.altruix.schedulingrequestmessageprocessor.defect201110141;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.silverstrike.pcc.impl.testutils.MockInjectorFactory;

import com.google.inject.Injector;

/**
 * @author DP118M
 * 
 */
public class TestDefect201110141 {
    public static final Logger LOGGER = LoggerFactory
            .getLogger(TestDefect201110141.class);

    /**
     * <pre>
     * 2011-10-13 10:08:18,864 ERROR [main] r.a.c.i.u.UncaughtExceptionHandler [UncaughtExceptionHandler.java:29] Uncaught exception
     * java.lang.NullPointerException: null
     * at java.util.LinkedList.addAll(LinkedList.java:286) ~[na:1.6.0_20]
     * at java.util.LinkedList.addAll(LinkedList.java:264) ~[na:1.6.0_20]
     * at co.altruix.pcc.api.schedulingrequestmessageprocessor.AbstractSchedulingRequestMessageProcessor.importDataFromGoogleTasks(AbstractSchedulingRequestMessageProcessor.java:197) ~[pcc-worker-1.14-jar-with-dependencies.jar:na]
     * at co.altruix.pcc.impl.immediatereschedulingrequestprocessor.DefaultImmediateSchedulingRequestMessageProcessor.run(DefaultImmediateSchedulingRequestMessageProcessor.java:59) ~[pcc-worker-1.14-jar-with-dependencies.jar:na]
     * at co.altruix.pcc.impl.dispatcher.DefaultDispatcher.processObjectMessage(DefaultDispatcher.java:102) ~[pcc-worker-1.14-jar-with-dependencies.jar:na]
     * at co.altruix.pcc.impl.dispatcher.DefaultDispatcher.run(DefaultDispatcher.java:54) ~[pcc-worker-1.14-jar-with-dependencies.jar:na]
     * at co.altruix.pcc.PccWorkerApp.run(PccWorkerApp.java:100) ~[pcc-worker-1.14-jar-with-dependencies.jar:na]
     * at co.altruix.pcc.PccWorkerApp.main(PccWorkerApp.java:220) ~[pcc-worker-1.14-jar-with-dependencies.jar:na]
     * </pre>
     */
    @Test
    public void testDefect201110141() {
        final MockInjectorFactory injectorFactory =
                new MockInjectorFactory(new MockInjectorModuleDefect201110141());
        final Injector injector = injectorFactory.createInjector();

        try {
            final MockAbstractSchedulingRequestMessageProcessorDefect201110141 objectUnderTest =
                    new MockAbstractSchedulingRequestMessageProcessorDefect201110141();
            objectUnderTest.setInjector(injector);
            objectUnderTest.run();
        } catch (final Exception exception) {
            LOGGER.error("", exception);
            Assert.fail(exception.getMessage());
        }
    }
}
