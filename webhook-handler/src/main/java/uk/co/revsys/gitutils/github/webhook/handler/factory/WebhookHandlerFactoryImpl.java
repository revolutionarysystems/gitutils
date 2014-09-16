package uk.co.revsys.gitutils.github.webhook.handler.factory;

import java.util.LinkedList;
import java.util.List;
import uk.co.revsys.gitutils.github.webhook.handler.WebhookHandler;

public class WebhookHandlerFactoryImpl implements WebhookHandlerFactory{

    private final List<WebhookHandler> webhookHandlers;

    public WebhookHandlerFactoryImpl(List<WebhookHandler> webhookHandlers) {
        this.webhookHandlers = webhookHandlers;
    }
    
    @Override
    public List<WebhookHandler> getWebhookHandlers(String repositoryUrl, String event) {
        List<WebhookHandler> handlers = new LinkedList<WebhookHandler>();
        for(WebhookHandler webhookHandler: webhookHandlers){
            if(webhookHandler.canHandle(repositoryUrl, event)){
                handlers.add(webhookHandler);
            }
        }
        return handlers;
    }

}
