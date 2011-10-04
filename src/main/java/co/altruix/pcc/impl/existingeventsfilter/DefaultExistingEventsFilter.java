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

import java.util.List;

import ru.altruix.commons.api.di.PccException;

import co.altruix.pcc.api.existingeventsfilter.ExistingEventsFilter;

import com.google.gdata.data.calendar.CalendarEventEntry;

/**
 * @author DP118M
 *
 */
final class DefaultExistingEventsFilter implements ExistingEventsFilter {
    private List<CalendarEventEntry> existingEvents;
    private List<CalendarEventEntry> eventsToDelete;
    private List<CalendarEventEntry> eventsToImport;
    
    public List<CalendarEventEntry> getEventsToDelete() {
        return eventsToDelete;
    }
    public List<CalendarEventEntry> getEventsToImport() {
        return eventsToImport;
    }
    public void setExistingEvents(final List<CalendarEventEntry> aExistingEvents) {
        this.existingEvents = aExistingEvents;
    }
    @Override
    public void run() throws PccException {
        // TODO Auto-generated method stub
    }

}
