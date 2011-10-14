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

package co.altruix.schedulingrequestmessageprocessor.defect201110141;

import java.util.List;

import ru.altruix.commons.api.di.PccException;
import at.silverstrike.pcc.api.model.UserData;

import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.inject.Injector;

import co.altruix.pcc.api.gcaleventimporter.GoogleCalendarEventImporter;

/**
 * @author DP118M
 *
 */
final class MockGoogleCalendarEventImporter implements
        GoogleCalendarEventImporter {

    @Override
    public void run() throws PccException {
    }

    @Override
    public void setInjector(Injector aInjector) {
    }

    @Override
    public void setCalendarScope(String aCalendarScope) {
    }

    @Override
    public void setConsumerKey(String aConsumerKey) {
    }

    @Override
    public void setUser(UserData aUser) {
    }

    @Override
    public List<CalendarEventEntry> getEventsToDelete() {
        return null;
    }

    @Override
    public List<CalendarEventEntry> getEventsToImport() {
        return null;
    }

}
