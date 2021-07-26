package com.codechallenge.mortalkombat.messaging;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;

public class Event<V> {

    public enum Type {CREATE}

    private Event.Type eventType;

    private String key;

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    private V data;

    private Instant instant;

    public Event() {
    }

    public Event(String key, V data) {
        this(Type.CREATE, key, data);
    }

    public Event(Type eventType, String key, V data) {
        this.eventType = eventType;
        this.key = key;
        this.data = data;
        this.instant = Instant.now();
    }

    public Type getEventType() {
        return this.eventType;
    }

    public String getKey() {
        return this.key;
    }

    public V getData() {
        return this.data;
    }

    public Instant getInstant() {
        return this.instant;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventType=" + this.eventType +
                ", key=" + this.key +
                ", data=" + this.data +
                ", instant=" + this.instant +
                '}';
    }
}
