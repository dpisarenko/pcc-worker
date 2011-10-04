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

import java.util.LinkedList;
import java.util.List;

import ru.altruix.commons.api.di.PccException;

import at.silverstrike.pcc.api.model.Event;
import at.silverstrike.pcc.api.model.SchedulingObject;
import at.silverstrike.pcc.api.persistence.Persistence;

import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.inject.Injector;

import co.altruix.pcc.api.calendarevent2pcceventconverter.CalendarEventEntry2PccEventConverter;

/**
 * @author DP118M
 * 
 */
public class DefaultCalendarEventEntry2PccEventConverter implements
        CalendarEventEntry2PccEventConverter {
    private List<CalendarEventEntry> calendarEventEntries;

    private List<SchedulingObject> pccEvents;
    private Persistence persistence;

    public List<SchedulingObject> getPccEvents() {
        return pccEvents;
    }

    public void setCalendarEventEntries(
            List<CalendarEventEntry> calendarEventEntries) {
        this.calendarEventEntries = calendarEventEntries;
    }

    @Override
    public void run() throws PccException {
        this.pccEvents = new LinkedList<SchedulingObject>();

        if (this.calendarEventEntries == null) {
            return;
        }
        long curId = 1;
        for (final CalendarEventEntry curEvent : this.calendarEventEntries) {
            final Event pccEvent =
                    this.persistence.createTransientEvent(curId++);
            pccEvent.setName(curEvent.getTitle().getPlainText());

        }
    }

    @Override
    public void setInjector(final Injector aInjector) {
        this.persistence = aInjector.getInstance(Persistence.class);
    }

}
