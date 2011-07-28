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

package co.altruix.pcc.api.di;

import java.util.Properties;

import ru.altruix.commons.api.di.InjectorFactory;

/**
 * @author DP118M
 *
 */
public interface PccWorkerInjectorFactory extends InjectorFactory{
    void setConfiguration(final Properties aConfiguration);
}
