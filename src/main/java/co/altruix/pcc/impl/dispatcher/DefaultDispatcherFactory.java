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

import co.altruix.pcc.api.dispatcher.Dispatcher;
import co.altruix.pcc.api.dispatcher.DispatcherFactory;

/**
 * @author DP118M
 *
 */
public final class DefaultDispatcherFactory implements DispatcherFactory {
    public Dispatcher create() {
        return new DefaultDispatcher();
    }
}
