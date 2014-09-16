package uk.co.revsys.gitutils.github.webhook.handler.filter;

public interface WebhookFilter {

    public boolean canHandle(String repository, String event);
    
}
