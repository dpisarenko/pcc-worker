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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.cdm.ImmediateSchedulingRequest;
import co.altruix.pcc.api.cdm.PccMessage;
import co.altruix.pcc.api.immediatereschedulingrequestprocessor.ImmediateSchedulingRequestMessageProcessor;

/**
 * @author DP118M
 * 
 */
class DefaultImmediateSchedulingRequestMessageProcessor implements
        ImmediateSchedulingRequestMessageProcessor {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultImmediateSchedulingRequestMessageProcessor.class);

    private PccMessage message;

    public void setMessage(final PccMessage aMessage) {
        this.message = aMessage;
    }

    public boolean isMessageProcessingSucceeded() {
        return true;
    }

    public void run() throws PccException {
        final ImmediateSchedulingRequest request = (ImmediateSchedulingRequest) this.message;
        
        LOGGER.debug("Immediate rescheduling request for user {}", request.getUserId());
    }

}
