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

package co.altruix.pcc.impl.dispatcher;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.cdm.PccMessage;
import co.altruix.pcc.api.channels.IncomingWorkerChannel;
import co.altruix.pcc.api.dispatcher.Dispatcher;
import co.altruix.pcc.api.messageprocessor.MessageProcessor;
import co.altruix.pcc.api.messageprocessorselector.MessageProcessorSelector;
import co.altruix.pcc.api.messageprocessorselector.MessageProcessorSelectorFactory;

/**
 * @author DP118M
 * 
 */
class DefaultDispatcher implements Dispatcher {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultDispatcher.class);
    private List<IncomingWorkerChannel> incomingChannels =
            new LinkedList<IncomingWorkerChannel>();
    private MessageProcessorSelector selector;
    private File testerLogFilePath;
    
    public void run() throws PccException {
        this.selector.setTesterLogFilePath(this.testerLogFilePath);
        
        for (final IncomingWorkerChannel curChannel : this.incomingChannels) {
            if (curChannel.newPccMessagesAvailable()) {
                final ObjectMessage message = curChannel.getNextPccMessage();
                final PccMessage curMessage = (PccMessage)message;

                selector.setPccMessage(curMessage);
                selector.run();

                final MessageProcessor processor =
                        selector.getMessageProcessor();

                if (processor != null) {
                    processor.setMessage(curMessage);
                    processor.run();

                    final boolean success =
                            processor.isMessageProcessingSucceeded();

                    LOGGER.info(
                            "Message processing result: {} on message '{}'",
                            new Object[] { success, curMessage });                    
                } else {
                    LOGGER.error("Cannot process message '{}'", curMessage);
                }
            }
            else if (curChannel.newMessagesAvailable())
            {
                final Message message = curChannel.getNextMessage();
                
                selector.setMessage(curChannel.getNextMessage());
            }
        }
    }

    public void addIncomingChannel(final IncomingWorkerChannel aChannel) {
        this.incomingChannels.add(aChannel);
    }

    public void setInjector(final Injector aInjector) {
        if (aInjector != null) {
            final MessageProcessorSelectorFactory factory =
                    aInjector
                            .getInstance(MessageProcessorSelectorFactory.class);
            this.selector = factory.create();
            this.selector.setInjector(aInjector);
        }
    }
    
    @Override
    public void setTesterLogFilePath(final File aTesterLogFilePath) {
        this.testerLogFilePath = aTesterLogFilePath;
    }
}
