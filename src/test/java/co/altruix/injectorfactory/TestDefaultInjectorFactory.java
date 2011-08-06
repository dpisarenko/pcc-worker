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

package co.altruix.injectorfactory;

import junit.framework.Assert;

import org.junit.Test;

import ru.altruix.commons.api.di.InjectorFactory;

import co.altruix.pcc.api.writeonlyqueuechannel.WriteOnlyQueueChannelFactory;
import co.altruix.pcc.impl.di.DefaultPccWorkerInjectorFactory;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;

/**
 * @author DP118M
 * 
 */
public class TestDefaultInjectorFactory {
    @Test
    public void test() {
        final InjectorFactory injectorFactory =
                new DefaultPccWorkerInjectorFactory();
        final Injector injector = injectorFactory.createInjector();

        Assert.assertNotNull(injector);

        try {
            injector.getInstance(WriteOnlyQueueChannelFactory.class);
        } catch (final ConfigurationException exception) {
            Assert.fail(exception.getMessage());
        }

    }
}
