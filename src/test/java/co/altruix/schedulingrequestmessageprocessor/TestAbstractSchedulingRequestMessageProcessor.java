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

package co.altruix.schedulingrequestmessageprocessor;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DP118M
 * 
 */
public class TestAbstractSchedulingRequestMessageProcessor {
    public static final Logger LOGGER = LoggerFactory
            .getLogger(TestAbstractSchedulingRequestMessageProcessor.class);

    /**
     * <pre>
     * Exception in thread "main" java.lang.IllegalArgumentException: Illegal pattern character 'i'
     * at java.text.SimpleDateFormat.compile(SimpleDateFormat.java:769)
     * at java.text.SimpleDateFormat.initialize(SimpleDateFormat.java:576)
     * at java.text.SimpleDateFormat.init(SimpleDateFormat.java:501)
     * at java.text.SimpleDateFormat.init(SimpleDateFormat.java:476)
     * at co.altruix.pcc.api.schedulingrequestmessageprocessor.AbstractSchedulingRequestMessageProcessor.
     *   getTimestampedFile(AbstractSchedulingRequestMessageProcessor.java:217)
     * at co.altruix.pcc.api.schedulingrequestmessageprocessor.AbstractSchedulingRequestMessageProcessor.
     * exportTasksToFile(AbstractSchedulingRequestMessageProcessor.java:205)
     * at co.altruix.pcc.impl.immediatereschedulingrequestprocessor.
     *  DefaultImmediateSchedulingRequestMessageProcessor.run(
     *          DefaultImmediateSchedulingRequestMessageProcessor.java:55)
     * at co.altruix.pcc.impl.dispatcher.DefaultDispatcher.processObjectMessage(DefaultDispatcher.java:102)
     * at co.altruix.pcc.impl.dispatcher.DefaultDispatcher.run(DefaultDispatcher.java:54)
     * at co.altruix.pcc.PccWorkerApp.run(PccWorkerApp.java:91)
     * at co.altruix.pcc.PccWorkerApp.main(PccWorkerApp.java:209)
     * 
     * </pre>
     */
    @Test
    public void testSetPccMessageAndMessage() {
        try {
            final MockAbstractSchedulingRequestMessageProcessor objectUnderTest =
                    new MockAbstractSchedulingRequestMessageProcessor();
            objectUnderTest.run();
        } catch (final Exception exception) {
            LOGGER.error("", exception);
            Assert.fail(exception.getMessage());
        }
    }
}
