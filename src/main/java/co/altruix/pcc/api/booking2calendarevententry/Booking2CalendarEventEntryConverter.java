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

package co.altruix.pcc.api.booking2calendarevententry;

import com.google.gdata.data.calendar.CalendarEventEntry;

import at.silverstrike.pcc.api.model.Booking;
import ru.altruix.commons.api.conventions.SingleActivityModule;

/**
 * @author DP118M
 * 
 */
public interface Booking2CalendarEventEntryConverter extends
        SingleActivityModule {
    final String PCC_EVENT_MARKER = " #PCC";
    
    void setBooking(final Booking aBooking);
    CalendarEventEntry getCalendarEventEntry();
}
