package com.example.shreeda.atmos2k16.Set;

/**
 * Created by SHREEDA on 06-10-2016.
 */

public class FeedSet {
    int event_id;
    String name;
    String message;
    long posted_time;

    public FeedSet(int event_id, String name, String message, long posted_time) {
        this.event_id = event_id;
        this.name = name;
        this.message = message;
        this.posted_time = posted_time;
    }

    public int getEvent_id() {
        return event_id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public long getPosted_time() {
        return posted_time;
    }
}
