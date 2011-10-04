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

package co.altruix.pcc.api.calendarevent2pcceventconverter;

import java.util.List;

import at.silverstrike.pcc.api.model.SchedulingObject;

import com.google.gdata.data.calendar.CalendarEventEntry;

import ru.altruix.commons.api.conventions.SingleActivityModule;
import ru.altruix.commons.api.di.ModuleWithInjectableDependencies;

/**
 * @author DP118M
 * 
 */
public interface CalendarEventEntry2PccEventConverter extends
        SingleActivityModule, ModuleWithInjectableDependencies {
    void setCalendarEventEntries(final List<CalendarEventEntry> aEntries);

    List<SchedulingObject> getPccEvents();
}
