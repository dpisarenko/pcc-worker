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

package co.altruix.pcc.api.messageprocessor;

import co.altruix.pcc.api.cdm.PccMessage;
import ru.altruix.commons.api.conventions.SingleActivityModule;

/**
 * @author DP118M
 *
 */
public interface MessageProcessor extends SingleActivityModule {
    void setMessage(final PccMessage aMessage);
    
    boolean isMessageProcessingSucceeded();
}
