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

import co.altruix.pcc.api.writeonlyqueuechannel.WriteOnlyQueueChannel;
import co.altruix.pcc.api.writeonlyqueuechannel.WriteOnlyQueueChannelFactory;

/**
 * @author DP118M
 *
 */
public class DefaultWriteOnlyQueueChannelFactory implements
        WriteOnlyQueueChannelFactory {

    @Override
    public WriteOnlyQueueChannel create() {
        return new DefaultWriteOnlyQueueChannel();
    }
}
