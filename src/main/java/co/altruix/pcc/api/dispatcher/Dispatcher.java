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

package co.altruix.pcc.api.dispatcher;

import java.io.File;

import ru.altruix.commons.api.conventions.SingleActivityModule;
import ru.altruix.commons.api.di.ModuleWithInjectableDependencies;
import co.altruix.pcc.api.channels.IncomingWorkerChannel;

/**
 * @author DP118M
 * 
 */
public interface Dispatcher extends SingleActivityModule,
        ModuleWithInjectableDependencies {
    void addIncomingChannel(final IncomingWorkerChannel aChannel);
    void setTesterLogFilePath(final File aTesterLogFilePath);
}
