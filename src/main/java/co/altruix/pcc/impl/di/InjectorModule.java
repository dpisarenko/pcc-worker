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

package co.altruix.pcc.impl.di;

import at.silverstrike.pcc.api.persistence.Persistence;
import at.silverstrike.pcc.impl.persistence.DefaultPersistence;
import co.altruix.pcc.api.dispatcher.DispatcherFactory;
import co.altruix.pcc.api.immediatereschedulingrequestprocessor.ImmediateSchedulingRequestMessageProcessorFactory;
import co.altruix.pcc.api.messageprocessorselector.MessageProcessorSelectorFactory;
import co.altruix.pcc.api.mq.MqInfrastructureInitializerFactory;
import co.altruix.pcc.api.queuechannel.QueueChannelFactory;
import co.altruix.pcc.api.shutdownhook.ShutdownHookFactory;
import co.altruix.pcc.impl.dispatcher.DefaultDispatcherFactory;
import co.altruix.pcc.impl.immediatereschedulingrequestprocessor.DefaultImmediateSchedulingRequestMessageProcessorFactory;
import co.altruix.pcc.impl.messageprocessorselector.DefaultMessageProcessorSelectorFactory;
import co.altruix.pcc.impl.mq.DefaultMqInfrastructureInitializerFactory;
import co.altruix.pcc.impl.queuechannel.DefaultQueueChannelFactory;
import co.altruix.pcc.impl.shutdownhook.DefaultShutdownHookFactory;

import com.google.inject.AbstractModule;

/**
 * @author DP118M
 * 
 */
class InjectorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DispatcherFactory.class)
                .toInstance(new DefaultDispatcherFactory());
        bind(ImmediateSchedulingRequestMessageProcessorFactory.class)
                .toInstance(
                        new DefaultImmediateSchedulingRequestMessageProcessorFactory());
        bind(MessageProcessorSelectorFactory.class).toInstance(
                new DefaultMessageProcessorSelectorFactory());
        bind(MqInfrastructureInitializerFactory.class).toInstance(
                new DefaultMqInfrastructureInitializerFactory());
        bind(QueueChannelFactory.class).toInstance(
                new DefaultQueueChannelFactory());
        bind(ShutdownHookFactory.class).toInstance(
                new DefaultShutdownHookFactory());
        bind(Persistence.class).toInstance(
                new DefaultPersistence());

    }
}
