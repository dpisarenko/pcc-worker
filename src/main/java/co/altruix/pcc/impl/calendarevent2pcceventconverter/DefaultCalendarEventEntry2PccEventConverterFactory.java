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

package co.altruix.pcc.impl.calendarevent2pcceventconverter;

import co.altruix.pcc.api.calendarevent2pcceventconverter.CalendarEventEntry2PccEventConverter;
import co.altruix.pcc.api.calendarevent2pcceventconverter.CalendarEventEntry2PccEventConverterFactory;

/**
 * @author DP118M
 *
 */
public class DefaultCalendarEventEntry2PccEventConverterFactory implements
        CalendarEventEntry2PccEventConverterFactory {

    @Override
    public CalendarEventEntry2PccEventConverter create() {
        return new DefaultCalendarEventEntry2PccEventConverter();
    }

}
