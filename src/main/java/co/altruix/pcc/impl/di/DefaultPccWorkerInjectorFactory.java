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

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;

import co.altruix.pcc.api.di.PccWorkerInjectorFactory;

public final class DefaultPccWorkerInjectorFactory implements PccWorkerInjectorFactory {
    private Properties configuration;
    
    public Injector createInjector() {
        final InjectorModule injectorModule = new InjectorModule(configuration);
        final Injector injector = Guice.createInjector(injectorModule);

        return injector;

    }

    public void setConfiguration(final Properties aConfiguration) {
        this.configuration = aConfiguration;
    }
}
