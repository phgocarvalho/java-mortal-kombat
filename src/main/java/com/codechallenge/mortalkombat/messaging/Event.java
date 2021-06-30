package com.codechallenge.mortalkombat.messaging;

import java.time.LocalDateTime;

public class Event<K, T> {

    public enum Type { CREATE, DELETE }

    private Event.Type eventType;
    private K key;
    private T data;
    private LocalDateTime creationDateTime;

    public Event(Type eventType, K key, T data) {
        this.eventType = eventType;
        this.key = key;
        this.data = data;
        this.creationDateTime = LocalDateTime.now();
    }

    public Type getEventType() {
        return eventType;
    }

    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventType=" + eventType +
                ", key=" + key +
                ", data=" + data +
                ", creationDateTime=" + creationDateTime +
                '}';
    }
}
