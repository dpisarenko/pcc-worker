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

import static co.altruix.pcc.api.booking2calendarevententry.Booking2CalendarEventEntryConverter.PCC_EVENT_MARKER;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ru.altruix.commons.api.di.PccException;

import co.altruix.pcc.api.existingeventsfilter.ExistingEventsFilter;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;

/**
 * @author DP118M
 * 
 */
final class DefaultExistingEventsFilter implements ExistingEventsFilter {
    private List<CalendarEventEntry> existingEvents;
    private List<CalendarEventEntry> eventsToDelete;
    private List<CalendarEventEntry> eventsToImport;
    private Date now;

    public List<CalendarEventEntry> getEventsToDelete() {
        return eventsToDelete;
    }

    public List<CalendarEventEntry> getEventsToImport() {
        return eventsToImport;
    }

    public void
            setExistingEvents(final List<CalendarEventEntry> aExistingEvents) {
        this.existingEvents = aExistingEvents;
    }

    @Override
    public void run() throws PccException {
        this.eventsToDelete = new LinkedList<CalendarEventEntry>();
        this.eventsToImport = new LinkedList<CalendarEventEntry>();

        if (this.existingEvents == null) {
            return;
        }

        for (final CalendarEventEntry curEvent : this.existingEvents) {
            final boolean pccGeneratedEvent = curEvent
                    .getTitle()
                    .getPlainText()
                    .endsWith(PCC_EVENT_MARKER);
            if (pccGeneratedEvent) {
                this.eventsToDelete.add(curEvent);
            } else if (!pccGeneratedEvent && eventInFuture(curEvent)) {
                this.eventsToImport.add(curEvent);
            }
        }
    }

    private boolean eventInFuture(final CalendarEventEntry aEvent) {
        final DateTime startDateTime = aEvent.getTimes().get(0).getStartTime();
        final Date startTime = new Date(startDateTime.getValue());

        return startTime.after(this.now);
    }

    @Override
    public void setNow(final Date aNow) {
        this.now = aNow;
    }
}
