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

package co.altruix.pcc.impl.eventexporter;

import co.altruix.pcc.api.eventexporter.EventExporter;
import co.altruix.pcc.api.eventexporter.EventExporterFactory;

/**
 * @author DP118M
 * 
 */
public final class DefaultEventExporterFactory implements EventExporterFactory {

    @Override
    public EventExporter create() {
        return new DefaultEventExporter();
    }

}
