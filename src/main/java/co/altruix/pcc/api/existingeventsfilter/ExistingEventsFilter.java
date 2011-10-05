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

package co.altruix.pcc.api.existingeventsfilter;

import java.util.Date;
import java.util.List;

import com.google.gdata.data.calendar.CalendarEventEntry;

import ru.altruix.commons.api.conventions.SingleActivityModule;

/**
 * @author DP118M
 *
 */
public interface ExistingEventsFilter extends SingleActivityModule {
    void setExistingEvents(final List<CalendarEventEntry> aEvents);
    void setNow(final Date aNow);
    List<CalendarEventEntry> getEventsToDelete();
    List<CalendarEventEntry> getEventsToImport();
}
