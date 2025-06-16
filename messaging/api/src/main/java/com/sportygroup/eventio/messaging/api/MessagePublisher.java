package com.sportygroup.eventio.messaging.api;

/**
 * Defines a contract for publishing messages.
 * Implementations of this interface should handle the specifics
 * of sending messages to a given destination.
 */
public interface MessagePublisher {

    /**
     * Publishes a message to a specified destination identified by the key.
     *
     * @param key     a string representing the destination or topic where the message should be published
     * @param message the message object to be sent to the specified destination
     */
    void publish(String key, Object message);
}
