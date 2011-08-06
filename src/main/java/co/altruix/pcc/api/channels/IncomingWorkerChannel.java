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

package co.altruix.pcc.api.channels;

import co.altruix.pcc.api.cdm.PccMessage;

public interface IncomingWorkerChannel extends WorkerChannel {    
    boolean newMessagesAvailable();
    PccMessage getNextMessage();
}
