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

package co.altruix.pcc.impl.writeonlyqueuechannel;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.writeonlyqueuechannel.WriteOnlyQueueChannel;

/**
 * @author DP118M
 * 
 */
class DefaultWriteOnlyQueueChannel implements WriteOnlyQueueChannel {
    private Session session;
    private String queueName;
    private MessageProducer producer;

    @Override
    public void init() throws PccException {
        try {
            final Destination destination = session.createQueue(this.queueName);

            producer = session.createProducer(destination);
        } catch (final JMSException exception) {
            throw new PccException(exception);
        }
    }

    @Override
    public void close() throws PccException {
        try {
            this.producer.close();
        } catch (final JMSException exception) {
            throw new PccException(exception);
        }

    }

    @Override
    public void setQueueName(final String aQueueName) {
        this.queueName = aQueueName;
    }

    @Override
    public void setSession(final Session aSession) {
        this.session = aSession;
    }

    @Override
    public void send(final Message aMessage) throws PccException {
        try {
            this.producer.send(aMessage);
        } catch (final JMSException exception) {
            throw new PccException(exception);
        }
    }
}
