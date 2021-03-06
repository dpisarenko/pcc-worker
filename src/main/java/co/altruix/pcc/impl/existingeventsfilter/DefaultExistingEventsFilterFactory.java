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

package co.altruix.pcc.impl.existingeventsfilter;

import co.altruix.pcc.api.existingeventsfilter.ExistingEventsFilter;
import co.altruix.pcc.api.existingeventsfilter.ExistingEventsFilterFactory;

/**
 * @author DP118M
 *
 */
public final class DefaultExistingEventsFilterFactory implements
        ExistingEventsFilterFactory {
    @Override
    public ExistingEventsFilter create() {
        return new DefaultExistingEventsFilter();
    }

}
