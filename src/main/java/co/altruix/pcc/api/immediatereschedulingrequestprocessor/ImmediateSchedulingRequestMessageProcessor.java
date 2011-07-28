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

package co.altruix.pcc.api.immediatereschedulingrequestprocessor;

import co.altruix.pcc.api.messageprocessor.MessageProcessor;

/**
 * @author DP118M
 * 
 */
public interface ImmediateSchedulingRequestMessageProcessor extends
        MessageProcessor {
    void setTaskJugglerPath(final String aTaskJugglerPath);

    void setClientSecret(final String aClientSecret);

    void setClientId(final String aClientId);

    void setAllCalendarsFeedUrl(final String aAllCalendarsFeedUrl);

    void setCalendarScope(final String aCalendarScope);

    void setConsumerKey(final String aConsumerKey);
}
