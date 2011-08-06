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

package co.altruix.pcc.api.messageprocessorselector;

import ru.altruix.commons.api.conventions.SingleActivityModule;
import ru.altruix.commons.api.di.ModuleWithInjectableDependencies;
import co.altruix.pcc.api.cdm.PccMessage;
import co.altruix.pcc.api.messageprocessor.MessageProcessor;
import co.altruix.pcc.api.outgoingqueuechannel.OutgoingQueueChannel;

/**
 * @author DP118M
 * 
 */
public interface MessageProcessorSelector extends SingleActivityModule,
        ModuleWithInjectableDependencies {
    void setMessage(final PccMessage aMessage);

    MessageProcessor getMessageProcessor();
    
    void setWorker2TesterChannel(final OutgoingQueueChannel aChannel);
}
