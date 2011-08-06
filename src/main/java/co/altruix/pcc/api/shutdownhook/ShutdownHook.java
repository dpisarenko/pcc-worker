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

package co.altruix.pcc.api.shutdownhook;

import javax.jms.Connection;
import javax.jms.Session;

import co.altruix.pcc.api.channels.WorkerChannel;

import ru.altruix.commons.api.conventions.SingleActivityModule;

/**
 * @author DP118M
 *
 */
public interface ShutdownHook extends SingleActivityModule {
    void setConnection(final Connection aConnection);
    void addChannel(final WorkerChannel aChannel);
    void setSession(final Session aSession);
}
