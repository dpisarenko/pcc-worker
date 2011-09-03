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

import javax.jms.Message;
import javax.jms.ObjectMessage;

public interface IncomingWorkerChannel extends WorkerChannel {    
    boolean newPccMessagesAvailable();
    ObjectMessage getNextPccMessage();
    
    boolean newMessagesAvailable();
    Message getNextMessage();
}
