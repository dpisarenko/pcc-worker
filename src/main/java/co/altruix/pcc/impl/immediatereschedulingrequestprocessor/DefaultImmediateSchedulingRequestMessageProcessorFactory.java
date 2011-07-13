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

package co.altruix.pcc.impl.immediatereschedulingrequestprocessor;

import co.altruix.pcc.api.immediatereschedulingrequestprocessor.ImmediateSchedulingRequestMessageProcessor;
import co.altruix.pcc.api.immediatereschedulingrequestprocessor.ImmediateSchedulingRequestMessageProcessorFactory;

/**
 * @author DP118M
 *
 */
public final class DefaultImmediateSchedulingRequestMessageProcessorFactory
        implements ImmediateSchedulingRequestMessageProcessorFactory {

    public ImmediateSchedulingRequestMessageProcessor create() {
        return new DefaultImmediateSchedulingRequestMessageProcessor();
    }

}
