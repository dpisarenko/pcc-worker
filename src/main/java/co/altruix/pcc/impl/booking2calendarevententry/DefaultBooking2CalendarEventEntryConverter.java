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

import org.apache.commons.lang.StringUtils;

import ru.altruix.commons.api.di.PccException;
import at.silverstrike.pcc.api.model.Booking;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.extensions.When;

import co.altruix.pcc.api.booking2calendarevententry.Booking2CalendarEventEntryConverter;

/**
 * @author DP118M
 * 
 */
final class DefaultBooking2CalendarEventEntryConverter implements
        Booking2CalendarEventEntryConverter {
    private Booking booking;
    private CalendarEventEntry calendarEventEntry;
    private String TITLE_TEMPLATE = "${title}${pccMarker}";

    @Override
    public void run() throws PccException {
        calendarEventEntry = new CalendarEventEntry();

        final String title = getTitle(booking.getProcess()
                        .getName());

        calendarEventEntry.setTitle(new PlainTextConstruct(title));

        final When eventTime = new When();
        final DateTime startDateTime =
                new DateTime(booking.getStartDateTime().getTime());
        final DateTime endDateTime =
                new DateTime(booking.getEndDateTime().getTime());

        eventTime.setStartTime(startDateTime);
        eventTime.setEndTime(endDateTime);

        calendarEventEntry.addTime(eventTime);
    }

    private String getTitle(final String aTaskName) {
        final String[] searchList = new String[] { "${title}", "${pccMarker}" };
        final String[] replacementList =
                new String[] { aTaskName,
                        Booking2CalendarEventEntryConverter.PCC_EVENT_MARKER };

        return StringUtils.replaceEach(TITLE_TEMPLATE, searchList,
                replacementList);
    }

    @Override
    public void setBooking(final Booking aBooking) {
        this.booking = aBooking;
    }

    @Override
    public CalendarEventEntry getCalendarEventEntry() {
        return this.calendarEventEntry;
    }

}
