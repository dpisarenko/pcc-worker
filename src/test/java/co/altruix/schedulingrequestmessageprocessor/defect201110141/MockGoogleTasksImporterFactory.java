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

import co.altruix.pcc.api.googletasksimporter.GoogleTasksImporter;
import co.altruix.pcc.api.googletasksimporter.GoogleTasksImporterFactory;

/**
 * @author DP118M
 *
 */
final class MockGoogleTasksImporterFactory implements
        GoogleTasksImporterFactory {

    @Override
    public GoogleTasksImporter create() {
        return new MockGoogleTasksImporter();
    }

}
