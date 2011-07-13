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

package co.altruix.pcc.api.queuechannel;

import javax.jms.Session;

import co.altruix.pcc.api.channels.PccChannel;

/**
 * @author DP118M
 *
 */
public interface QueueChannel extends PccChannel {
    void setQueueName(final String aQueueName);
    void setSession(final Session aSession);
}
