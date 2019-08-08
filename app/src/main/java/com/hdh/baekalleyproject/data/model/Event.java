package com.hdh.baekalleyproject.data.model;

public class Event {
    private String eventImageURL;

    public Event(String eventImageURL) {
        this.eventImageURL = eventImageURL;
    }

    public String getEventImageURL() {
        return eventImageURL;
    }

    public void setEventImageURL(String eventImageURL) {
        this.eventImageURL = eventImageURL;
    }
}
