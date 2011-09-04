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

import javax.jms.ObjectMessage;

import org.apache.activemq.command.ActiveMQObjectMessage;

import co.altruix.testutils.IncomingQueueChannelAdapter;

/**
 * @author DP118M
 *
 */
class MockChannel extends IncomingQueueChannelAdapter {

    @Override
    public boolean newPccMessagesAvailable() {
        return true;
    }

    @Override
    public ObjectMessage getNextPccMessage() {
        return new ActiveMQObjectMessage();
    }

}
