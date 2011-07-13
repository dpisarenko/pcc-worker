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

package co.altruix.pcc.impl.shutdownhook;

import co.altruix.pcc.api.shutdownhook.ShutdownHook;
import co.altruix.pcc.api.shutdownhook.ShutdownHookFactory;

/**
 * @author DP118M
 *
 */
public class DefaultShutdownHookFactory implements ShutdownHookFactory {

    public ShutdownHook create() {
        return new DefaultShutdownHook();
    }

}
