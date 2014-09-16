package uk.co.revsys.gitutils.github.webhook.handler;

import org.json.JSONObject;

public interface WebhookHandler {

    public void handle(String requestId, String repositoryUrl, String event, JSONObject json) throws WebhookException;
    
    public boolean canHandle(String repository, String event);
    
}
