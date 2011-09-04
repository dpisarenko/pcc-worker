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

package co.altruix.testutils;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.incomingqueuechannel.IncomingQueueChannel;

/**
 * @author DP118M
 *
 */
public abstract class IncomingQueueChannelAdapter implements
        IncomingQueueChannel {

    @Override
    public boolean newPccMessagesAvailable() {
        return false;
    }

    @Override
    public ObjectMessage getNextPccMessage() {
        return null;
    }

    @Override
    public boolean newMessagesAvailable() {
        return false;
    }

    @Override
    public Message getNextMessage() {
        return null;
    }

    @Override
    public void init() throws PccException {

    }

    @Override
    public void close() throws PccException {

    }

    @Override
    public void setSession(final Session aSession) {

    }

    @Override
    public void setQueueName(final String aQueueName) {

    }

}
