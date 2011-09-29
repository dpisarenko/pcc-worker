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

package co.altruix.pcc.impl.booking2calendarevententry;

import co.altruix.pcc.api.booking2calendarevententry.Booking2CalendarEventEntryConverter;
import co.altruix.pcc.api.booking2calendarevententry.Booking2CalendarEventEntryConverterFactory;

/**
 * @author DP118M
 * 
 */
public class DefaultBooking2CalendarEventEntryConverterFactory implements
        Booking2CalendarEventEntryConverterFactory {

    @Override
    public Booking2CalendarEventEntryConverter create() {
        return new DefaultBooking2CalendarEventEntryConverter();
    }

}
