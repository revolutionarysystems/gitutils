package uk.co.revsys.gitutils.github.webhook.handler.factory;

import java.util.List;
import uk.co.revsys.gitutils.github.webhook.handler.WebhookHandler;

public interface WebhookHandlerFactory {

    public List<WebhookHandler> getWebhookHandlers(String repositoryUrl, String event);
    
}
