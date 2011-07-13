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

package co.altruix.pcc.api.mq;

import javax.jms.Connection;
import javax.jms.Session;

import ru.altruix.commons.api.conventions.SingleActivityModule;

/**
 * @author DP118M
 *
 */
public interface MqInfrastructureInitializer extends SingleActivityModule {
    void setUsername(final String aUser);
    void setPassword(final String aPassword);
    void setBrokerUrl(final String aUrl);
    Session getSession();
    Connection getConnection();
}
