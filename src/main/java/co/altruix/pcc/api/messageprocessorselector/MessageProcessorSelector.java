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

package co.altruix.pcc.api.messageprocessorselector;

import java.io.File;

import javax.jms.Message;

import ru.altruix.commons.api.conventions.SingleActivityModule;
import ru.altruix.commons.api.di.ModuleWithInjectableDependencies;
import co.altruix.pcc.api.cdm.PccMessage;
import co.altruix.pcc.api.messageprocessor.MessageProcessor;

/**
 * @author DP118M
 * 
 */
public interface MessageProcessorSelector extends SingleActivityModule,
        ModuleWithInjectableDependencies {
    void setPccMessage(final PccMessage aMessage);
    
    void setMessage(final Message aMessage);
    
    MessageProcessor getMessageProcessor();
    
    void setTesterLogFilePath(final File aTesterLogFilePath);
}
