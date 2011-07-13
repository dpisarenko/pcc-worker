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

package co.altruix.pcc.impl.mq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.mq.MqInfrastructureInitializer;

/**
 * @author DP118M
 * 
 */
class DefaultMqInfrastructureInitializer implements
        MqInfrastructureInitializer {
    private String username;
    private String password;
    private String url;
    private Session session;
    private Connection connection;

    public void run() throws PccException {
        try {
            final ActiveMQConnectionFactory connectionFactory =
                    new ActiveMQConnectionFactory(this.username, this.password,
                            this.url);

            connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            session =
                    connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (final JMSException exception) {
            throw new PccException(exception);
        }
    }

    public void setUsername(final String aUser) {
        this.username = aUser;
    }

    public void setPassword(final String aPassword) {
        this.password = aPassword;
    }

    public void setBrokerUrl(final String aUrl) {
        this.url = aUrl;
    }

    public Session getSession() {
        return session;
    }

    public Connection getConnection() {
        return connection;
    }

}
