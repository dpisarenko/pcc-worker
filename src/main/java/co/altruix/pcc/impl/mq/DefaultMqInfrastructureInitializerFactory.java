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

package co.altruix.pcc.impl.mq;

import co.altruix.pcc.api.mq.MqInfrastructureInitializer;
import co.altruix.pcc.api.mq.MqInfrastructureInitializerFactory;

/**
 * @author DP118M
 *
 */
public final class DefaultMqInfrastructureInitializerFactory implements
        MqInfrastructureInitializerFactory {

    public MqInfrastructureInitializer create() {
        return new DefaultMqInfrastructureInitializer();
    }

}
