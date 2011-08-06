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

package co.altruix.pcc.api.channels;

import javax.jms.Session;

import ru.altruix.commons.api.di.PccException;

/**
 * @author DP118M
 *
 */
public interface WorkerChannel {
    void init() throws PccException;
    void close() throws PccException;
    void setSession(final Session aSession);
}
