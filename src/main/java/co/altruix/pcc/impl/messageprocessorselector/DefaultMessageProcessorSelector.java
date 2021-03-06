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

package co.altruix.pcc.impl.messageprocessorselector;

import java.io.File;

import javax.jms.Message;

import com.google.inject.Injector;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.cdm.PccMessage;
import co.altruix.pcc.api.immediatereschedulingrequestprocessor.ImmediateSchedulingRequestMessageProcessor;
import co.altruix.pcc.api.immediatereschedulingrequestprocessor.ImmediateSchedulingRequestMessageProcessorFactory;
import co.altruix.pcc.api.messageprocessor.MessageProcessor;
import co.altruix.pcc.api.messageprocessorselector.MessageProcessorSelector;
import co.altruix.pcc.impl.cdm.DefaultImmediateSchedulingRequest;

/**
 * @author DP118M
 * 
 */
class DefaultMessageProcessorSelector implements MessageProcessorSelector {
    private PccMessage message;
    private MessageProcessor processor;
    private ImmediateSchedulingRequestMessageProcessor immediateSchedulingRequestMessageProcessor;

    public void run() throws PccException {

        if (this.message == null) {
            this.processor = null;
        } else if (this.message instanceof DefaultImmediateSchedulingRequest) {

            this.processor = this.immediateSchedulingRequestMessageProcessor;
        } else {
            this.processor = null;
        }
    }

    public void setPccMessage(final PccMessage aMessage) {
        this.message = aMessage;
    }

    public MessageProcessor getMessageProcessor() {
        return this.processor;
    }

    public void setInjector(final Injector aInjector) {
        if (aInjector != null) {
            final ImmediateSchedulingRequestMessageProcessorFactory factory =
                    aInjector
                            .getInstance(ImmediateSchedulingRequestMessageProcessorFactory.class);
            immediateSchedulingRequestMessageProcessor = factory.create();
            immediateSchedulingRequestMessageProcessor.setInjector(aInjector);
        }
    }

    @Override
    public void setTesterLogFilePath(final File aTesterLogFilePath) {
        this.immediateSchedulingRequestMessageProcessor
                .setTesterLogFilePath(aTesterLogFilePath);
    }

    @Override
    public void setMessage(final Message aMessage) {
        // TODO Auto-generated method stub
        
    }
}
