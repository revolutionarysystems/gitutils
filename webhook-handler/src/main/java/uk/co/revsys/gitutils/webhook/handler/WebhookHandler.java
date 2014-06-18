package uk.co.revsys.gitutils.webhook.handler;

public interface WebhookHandler {

    public void handle(String id, String signature, String event, String json) throws WebhookException;
    
}
