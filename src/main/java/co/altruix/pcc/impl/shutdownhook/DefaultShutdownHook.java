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

package co.altruix.pcc.impl.shutdownhook;

import java.util.LinkedList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.channels.PccChannel;
import co.altruix.pcc.api.shutdownhook.ShutdownHook;

/**
 * @author DP118M
 * 
 */
class DefaultShutdownHook implements ShutdownHook {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultShutdownHook.class);

    private List<PccChannel> channels = new LinkedList<PccChannel>();
    private Connection connection;
    private Session session;

    public void run() throws PccException {
        // TODO Auto-generated method stub
        for (final PccChannel curChannel : this.channels) {
            try {
                curChannel.close();
            } catch (final PccException exception) {
                LOGGER.error("", exception);
            }
        }

        try {
            this.session.close();
        } catch (final JMSException exception) {
            LOGGER.error("", exception);
        }

        try {
            this.connection.close();
        } catch (final JMSException exception) {
            LOGGER.error("", exception);
        }
    }

    public void setConnection(final Connection aConnection) {
        connection = aConnection;
    }

    public void addChannel(final PccChannel aChannel) {
        this.channels.add(aChannel);
    }

    public void setSession(final Session aSession) {
        this.session = aSession;
    }
}
