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

package co.altruix.pcc.impl.messageprocessorselector;

import co.altruix.pcc.api.messageprocessorselector.MessageProcessorSelector;
import co.altruix.pcc.api.messageprocessorselector.MessageProcessorSelectorFactory;

/**
 * @author DP118M
 * 
 */
public final class DefaultMessageProcessorSelectorFactory implements
        MessageProcessorSelectorFactory {

    public MessageProcessorSelector create() {
        return new DefaultMessageProcessorSelector();
    }

}
