package com.spatialkey;

import java.util.Arrays;
import java.util.Map;

/**
 * A location that is enhanced with the impact/depth data for a set of events
 */
public class EnhancedLocation extends Location {
    public static class EventEnhancement {
        private String eventPeril;
        private long eventImpact;

        public EventEnhancement(String name, long eventImpact) {
            this.eventPeril = name;
            this.eventImpact = eventImpact;
        }

        public String getEventPeril() {
            return eventPeril;
        }

        public void setEventPeril(String eventPeril) {
            this.eventPeril = eventPeril;
        }

        public long getEventImpact() {
            return eventImpact;
        }

        public void setEventImpact(long eventImpact) {
            this.eventImpact = eventImpact;
        }
    }

    private EventEnhancement[] enhancedEvents;

    public EnhancedLocation(String address, Double totalInsuredValue) {
        super(address, totalInsuredValue);
        enhancedEvents = new EventEnhancement[0];
    }

    public void addEnhancement(String eventName, long eventImpact) {
        enhancedEvents = Arrays.copyOf(enhancedEvents, enhancedEvents.length+1);
        enhancedEvents[enhancedEvents.length-1] = new EventEnhancement(eventName, eventImpact);
    }

    public String[] getEnhancedEvents() {
        return (String[]) Arrays.stream(enhancedEvents).map(f -> f.getEventPeril()).distinct().toArray();
    }

    public long getEventImpact(String name) {
        for(int i = 0; i < enhancedEvents.length; i++) {
            if(name == enhancedEvents[i].eventPeril) {
                return enhancedEvents[i].eventImpact;
            }
        }

        return 0;
    }
}
