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

import at.silverstrike.pcc.api.model.UserData;
import at.silverstrike.pcc.api.persistence.Persistence;

import com.google.inject.Injector;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.cdm.PccMessage;
import co.altruix.pcc.api.immediatereschedulingrequestprocessor.ImmediateSchedulingRequestMessageProcessor;
import co.altruix.pcc.impl.cdm.DefaultImmediateSchedulingRequest;

/**
 * @author DP118M
 * 
 */
class DefaultImmediateSchedulingRequestMessageProcessor implements
        ImmediateSchedulingRequestMessageProcessor {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultImmediateSchedulingRequestMessageProcessor.class);

    private Persistence persistence;
    
    private PccMessage message;

    public void setMessage(final PccMessage aMessage) {
        this.message = aMessage;
    }

    public boolean isMessageProcessingSucceeded() {
        return true;
    }

    public void run() throws PccException {
        final DefaultImmediateSchedulingRequest request = (DefaultImmediateSchedulingRequest) this.message;
                
        UserData userData = persistence.getUser(request.getUserId());
        
        LOGGER.debug("Immediate rescheduling request for user {}", userData.getUsername());
    }
    
    public void setInjector(final Injector aInjector) {
        if (aInjector != null) {
            this.persistence = aInjector.getInstance(Persistence.class);
        }
    }

}
