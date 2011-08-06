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

package co.altruix.pcc.impl.outgoingqueuechannel;

import co.altruix.pcc.api.outgoingqueuechannel.OutgoingQueueChannel;
import co.altruix.pcc.api.outgoingqueuechannel.OutgoingQueueChannelFactory;

/**
 * @author DP118M
 *
 */
public class DefaultOutgoingQueueChannelFactory implements
        OutgoingQueueChannelFactory {

    @Override
    public OutgoingQueueChannel create() {
        return new DefaultOutgoingQueueChannel();
    }
}
