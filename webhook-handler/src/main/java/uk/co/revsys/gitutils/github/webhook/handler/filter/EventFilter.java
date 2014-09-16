package uk.co.revsys.gitutils.github.webhook.handler.filter;

import java.util.LinkedList;
import java.util.List;

public class EventFilter implements WebhookFilter{

    private List<String> events;
    
    public EventFilter(String event){
        this.events = new LinkedList<String>();
        events.add(event);
    }

    public EventFilter(List<String> events) {
        this.events = events;
    }
    
    @Override
    public boolean canHandle(String repository, String event) {
        return events.contains(event);
    }
    
}
