package uk.co.revsys.gitutils.webhook.handler;

public interface WebhookHandler {

    public void handle(String id, String event, String json) throws WebhookException;
    
}
