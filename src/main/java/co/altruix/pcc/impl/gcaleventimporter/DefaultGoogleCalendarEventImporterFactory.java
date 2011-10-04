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

package co.altruix.pcc.impl.gcaleventimporter;

import co.altruix.pcc.api.gcaleventimporter.GoogleCalendarEventImporter;
import co.altruix.pcc.api.gcaleventimporter.GoogleCalendarEventImporterFactory;

/**
 * @author DP118M
 * 
 */
public class DefaultGoogleCalendarEventImporterFactory implements
        GoogleCalendarEventImporterFactory {

    @Override
    public GoogleCalendarEventImporter create() {
        return new DefaultGoogleCalendarEventImporter();
    }

}
