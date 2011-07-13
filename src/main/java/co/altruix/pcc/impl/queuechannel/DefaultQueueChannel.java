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

package co.altruix.pcc.impl.queuechannel;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.altruix.commons.api.di.PccException;

import co.altruix.pcc.api.cdm.PccMessage;
import co.altruix.pcc.api.queuechannel.QueueChannel;

/**
 * @author DP118M
 * 
 */
class DefaultQueueChannel implements QueueChannel {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultQueueChannel.class);

    private Session session;
    private String queueName;
    private MessageConsumer consumer;
    private Message nextMessage;

    public boolean newMessagesAvailable() {
        try {
            this.nextMessage = this.consumer.receiveNoWait();
        } catch (final JMSException exception) {
            this.nextMessage = null;
            LOGGER.error("", exception);
        }

        return (this.nextMessage != null);
    }

    public PccMessage getNextMessage() {
        return (PccMessage) this.nextMessage;
    }

    public void init() throws PccException {
        try {
            final Destination destination = session.createQueue(this.queueName);

            consumer = session.createConsumer(destination);
        } catch (final JMSException exception) {
            throw new PccException(exception);
        }
    }

    public void setQueueName(final String aQueueName) {
        this.queueName = aQueueName;
    }

    public void setSession(final Session aSession) {
        this.session = aSession;
    }

    public void close() throws PccException {
        try {
            this.consumer.close();
        } catch (final JMSException exception) {
            throw new PccException(exception);
        }
    }

}
