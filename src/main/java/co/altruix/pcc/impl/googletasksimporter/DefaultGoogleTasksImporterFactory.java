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

package co.altruix.pcc.impl.googletasksimporter;

import co.altruix.pcc.api.googletasksimporter.GoogleTasksImporter;
import co.altruix.pcc.api.googletasksimporter.GoogleTasksImporterFactory;

/**
 * @author DP118M
 *
 */
public class DefaultGoogleTasksImporterFactory implements
        GoogleTasksImporterFactory {

    @Override
    public final GoogleTasksImporter create() {
        return new DefaultGoogleTasksImporter();
    }

}
