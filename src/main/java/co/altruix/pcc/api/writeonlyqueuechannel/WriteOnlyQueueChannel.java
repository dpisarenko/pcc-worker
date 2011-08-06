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

package co.altruix.pcc.api.writeonlyqueuechannel;

import javax.jms.Message;
import javax.jms.Session;

import ru.altruix.commons.api.di.PccException;

/**
 * @author DP118M
 *
 */
public interface WriteOnlyQueueChannel {
    void init() throws PccException;
    void close() throws PccException;
    void setQueueName(final String aQueueName);
    void setSession(final Session aSession);
    void send(final Message aMessage) throws PccException;
}
