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

package co.altruix.pcc;

import javax.jms.Connection;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.silverstrike.pcc.api.persistence.Persistence;

import com.google.inject.Injector;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.dispatcher.Dispatcher;
import co.altruix.pcc.api.dispatcher.DispatcherFactory;
import co.altruix.pcc.api.mq.MqInfrastructureInitializer;
import co.altruix.pcc.api.mq.MqInfrastructureInitializerFactory;
import co.altruix.pcc.api.queuechannel.QueueChannel;
import co.altruix.pcc.api.queuechannel.QueueChannelFactory;
import co.altruix.pcc.api.shutdownhook.ShutdownHook;
import co.altruix.pcc.api.shutdownhook.ShutdownHookFactory;
import co.altruix.pcc.impl.di.DefaultPccWorkerInjectorFactory;

public class PccWorkerApp {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PccWorkerApp.class);

    void run() throws PccException {
        final DefaultPccWorkerInjectorFactory injectorFactory =
                new DefaultPccWorkerInjectorFactory();
        final Injector injector = injectorFactory.createInjector();

        final Persistence persistence = injector.getInstance(Persistence.class);
        
        persistence.openSession();
        
        final MqInfrastructureInitializer mqInitializer = initMq(injector);

        final Session session = mqInitializer.getSession();

        setupShutdownHook(injector, session,
                mqInitializer.getConnection());

        final QueueChannelFactory channelFactory =
                injector.getInstance(QueueChannelFactory.class);
        final QueueChannel web2workerQueue = channelFactory.create();

        web2workerQueue.setQueueName("PCC.WEB.WORKER");
        web2workerQueue.setSession(session);
        web2workerQueue.init();

        final Dispatcher dispatcher = getDispatcher(injector);

        dispatcher.addChannel(web2workerQueue);

        while (true) {
            try {
                dispatcher.run();
            } catch (final PccException exception) {
                LOGGER.error("", exception);
            }

            try {
                Thread.sleep(500);
            } catch (final InterruptedException exception) {
                LOGGER.error("", exception);
            }
        }
    }

    private Dispatcher getDispatcher(final Injector aInjector) {
        final DispatcherFactory factory =
                aInjector.getInstance(DispatcherFactory.class);
        final Dispatcher dispatcher = factory.create();
        dispatcher.setInjector(aInjector);
        return dispatcher;
    }

    private MqInfrastructureInitializer initMq(final Injector injector)
            throws PccException {
        final MqInfrastructureInitializerFactory factory =
                injector.getInstance(MqInfrastructureInitializerFactory.class);
        final MqInfrastructureInitializer mqInitializer = factory.create();

        mqInitializer.setBrokerUrl("");
        mqInitializer.setPassword("");
        mqInitializer.setBrokerUrl("failover://tcp://localhost:61616");
        mqInitializer.run();
        return mqInitializer;
    }

    private void setupShutdownHook(final Injector injector,
            final Session aSession, final Connection aConnection) {
        final ShutdownHookFactory factory =
                injector.getInstance(ShutdownHookFactory.class);
        final ShutdownHook hook = factory.create();

        hook.setSession(aSession);
        hook.setConnection(aConnection);

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                try {
                    hook.run();
                } catch (final PccException exception) {
                    LOGGER.error("", exception);
                }
            }
        });
    }

    public static void main(String[] args) throws PccException {
        final PccWorkerApp app = new PccWorkerApp();
        
        app.run();
    }
}
