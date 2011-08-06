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

import co.altruix.pcc.api.incomingqueuechannel.IncomingQueueChannel;
import co.altruix.pcc.api.incomingqueuechannel.IncomingQueueChannelFactory;

/**
 * @author DP118M
 * 
 */
public final class DefaultIncomingQueueChannelFactory implements
        IncomingQueueChannelFactory {
    public IncomingQueueChannel create() {
        return new DefaultIncomingQueueChannel();
    }

}
