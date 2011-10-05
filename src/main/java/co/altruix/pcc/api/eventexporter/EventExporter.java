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

package co.altruix.pcc.api.eventexporter;

import java.io.File;
import java.util.List;

import com.google.gdata.data.calendar.CalendarEventEntry;

import ru.altruix.commons.api.conventions.SingleActivityModule;

/**
 * @author DP118M
 *
 */
public interface EventExporter extends SingleActivityModule {

    void setEventsToExport(final List<CalendarEventEntry> aEvents);

    void setTargetFile(final File aFile);
    

}
