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

import co.altruix.pcc.api.gcaleventimporter.GoogleCalendarEventImporter;
import co.altruix.pcc.api.gcaleventimporter.GoogleCalendarEventImporterFactory;

/**
 * @author DP118M
 *
 */
final class MockGoogleCalendarEventImporterFactory implements
        GoogleCalendarEventImporterFactory {

    @Override
    public GoogleCalendarEventImporter create() {
        return new MockGoogleCalendarEventImporter();
    }

}
