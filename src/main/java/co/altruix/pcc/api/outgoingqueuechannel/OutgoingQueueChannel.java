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

package co.altruix.pcc.api.outgoingqueuechannel;

import co.altruix.pcc.api.channels.OutgoingWorkerChannel;

/**
 * @author DP118M
 *
 */
public interface OutgoingQueueChannel extends OutgoingWorkerChannel {
    void setQueueName(final String aQueueName);
    
    void setChannelName(final String aChannelName);
    String getChannelName();
}
