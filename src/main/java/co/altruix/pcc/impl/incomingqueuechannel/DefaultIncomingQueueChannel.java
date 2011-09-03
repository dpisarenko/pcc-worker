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

package co.altruix.pcc.impl.incomingqueuechannel;

import java.util.LinkedList;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.altruix.commons.api.di.PccException;

import co.altruix.pcc.api.cdm.PccMessage;
import co.altruix.pcc.api.incomingqueuechannel.IncomingQueueChannel;

/**
 * @author DP118M
 * 
 */
class DefaultIncomingQueueChannel implements IncomingQueueChannel,
        MessageListener {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultIncomingQueueChannel.class);

    private Session session;
    private String queueName;
    private MessageConsumer consumer;

    private List<ObjectMessage> pccMessages = new LinkedList<ObjectMessage>();
    private List<Message> messages = new LinkedList<Message>();

    public boolean newPccMessagesAvailable() {
        return this.pccMessages.size() > 0;
    }

    public ObjectMessage getNextPccMessage() {
        final ObjectMessage returnValue = this.pccMessages.get(0);
        this.pccMessages.remove(returnValue);
        return returnValue;
    }

    public void init() throws PccException {
        try {
            final Destination destination = session.createQueue(this.queueName);

            consumer = session.createConsumer(destination);
            consumer.setMessageListener(this);
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

    @Override
    public boolean newMessagesAvailable() {
        return this.messages.size() > 0;
    }

    @Override
    public Message getNextMessage() {
        final Message returnValue = this.messages.get(0);
        this.messages.remove(returnValue);
        return returnValue;

    }

    @Override
    public void onMessage(final Message aMessage) {
        try {
            if (aMessage instanceof ObjectMessage) {
                final ObjectMessage objectMessage = (ObjectMessage) aMessage;
                final Object object = objectMessage.getObject();

                if ((object != null) && (object instanceof PccMessage)) {
                    this.pccMessages.add(objectMessage);
                }
            } else {
                this.messages.add(aMessage);
            }
        } catch (final JMSException exception) {
            LOGGER.error("", exception);
        }
    }

}
