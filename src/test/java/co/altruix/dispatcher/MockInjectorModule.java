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

package co.altruix.dispatcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import junit.framework.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.silverstrike.pcc.api.persistence.Persistence;
import at.silverstrike.pcc.impl.persistence.DefaultPersistence;
import co.altruix.pcc.api.dispatcher.DispatcherFactory;
import co.altruix.pcc.api.immediatereschedulingrequestprocessor.ImmediateSchedulingRequestMessageProcessorFactory;
import co.altruix.pcc.api.messageprocessorselector.MessageProcessorSelectorFactory;
import co.altruix.pcc.impl.dispatcher.DefaultDispatcherFactory;
import co.altruix.pcc.impl.immediatereschedulingrequestprocessor.DefaultImmediateSchedulingRequestMessageProcessorFactory;
import co.altruix.pcc.impl.messageprocessorselector.DefaultMessageProcessorSelectorFactory;

import com.google.inject.AbstractModule;

/**
 * @author DP118M
 * 
 */
class MockInjectorModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MockInjectorModule.class);

    @Override
    protected void configure() {
        bind(DispatcherFactory.class)
                .toInstance(new DefaultDispatcherFactory());

        bind(MessageProcessorSelectorFactory.class)
                .toInstance(new DefaultMessageProcessorSelectorFactory());

        final Properties properties = new Properties();

        try {
            final FileInputStream fis =
                    new FileInputStream(
                            "src/test/resources/co/altruix/dispatcher/conf.properties");
            properties.load(fis);
        } catch (final FileNotFoundException exception) {
            LOGGER.error("", exception);
            Assert.fail(exception.getMessage());
        } catch (final IOException exception) {
            LOGGER.error("", exception);
            Assert.fail(exception.getMessage());
        }

        bind(ImmediateSchedulingRequestMessageProcessorFactory.class)
                .toInstance(
                        new DefaultImmediateSchedulingRequestMessageProcessorFactory(
                                properties));
        bind(Persistence.class).toInstance(new DefaultPersistence());
    }

}
