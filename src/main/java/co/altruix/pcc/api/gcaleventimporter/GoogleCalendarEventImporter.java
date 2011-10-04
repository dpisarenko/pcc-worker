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

package co.altruix.pcc.api.gcaleventimporter;

import java.util.List;

import at.silverstrike.pcc.api.model.UserData;

import com.google.gdata.data.calendar.CalendarEventEntry;

import ru.altruix.commons.api.conventions.SingleActivityModule;
import ru.altruix.commons.api.di.ModuleWithInjectableDependencies;

/**
 * @author DP118M
 *
 */
public interface GoogleCalendarEventImporter extends SingleActivityModule,
        ModuleWithInjectableDependencies {
    void setCalendarScope(final String aCalendarScope);
    void setConsumerKey(final String aConsumerKey);
    void setUser(final UserData aUser);

    List<CalendarEventEntry> getEventsToDelete();
    List<CalendarEventEntry> getEventsToImport();
}
