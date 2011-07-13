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

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.cdm.PccMessage;
import co.altruix.pcc.api.channels.PccChannel;
import co.altruix.pcc.api.dispatcher.Dispatcher;
import co.altruix.pcc.api.messageprocessor.MessageProcessor;
import co.altruix.pcc.api.messageprocessorselector.MessageProcessorSelector;

/**
 * @author DP118M
 * 
 */
class DefaultDispatcher implements Dispatcher {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultDispatcher.class);

    private List<PccChannel> channels = new LinkedList<PccChannel>();
    private MessageProcessorSelector selector;

    public void run() throws PccException {
        for (final PccChannel curChannel : this.channels) {
            if (curChannel.newMessagesAvailable()) {
                final PccMessage curMessage = curChannel.getNextMessage();

                selector.setMessage(curMessage);
                selector.run();

                final MessageProcessor processor =
                        selector.getMessageProcessor();

                if (processor != null) {
                    processor.setMessage(curMessage);
                    processor.run();

                    final boolean success =
                            processor.isMessageProcessingSucceeded();

                    curChannel.removeFromChannel(curMessage);

                    LOGGER.info(
                            "Message processing result: {} on message '{}'",
                            new Object[] { success, curMessage });
                } else {
                    LOGGER.error("Cannot process message '{}'", curMessage);
                }
            }
        }
    }

    public void addChannel(final PccChannel aChannel) {
        this.channels.add(aChannel);
    }

    public void setInjector(final Injector aInjector) {
        if (aInjector != null) {
            this.selector =
                    aInjector.getInstance(MessageProcessorSelector.class);
        }
    }

}
