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

import co.altruix.pcc.api.queuechannel.QueueChannel;
import co.altruix.pcc.api.queuechannel.QueueChannelFactory;

/**
 * @author DP118M
 *
 */
public final class DefaultQueueChannelFactory implements QueueChannelFactory {
    public QueueChannel create() {
        return new DefaultQueueChannel();
    }

}
