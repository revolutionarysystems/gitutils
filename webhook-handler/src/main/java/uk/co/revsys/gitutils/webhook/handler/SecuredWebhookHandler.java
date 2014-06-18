package uk.co.revsys.gitutils.webhook.handler;

public abstract class SecuredWebhookHandler implements WebhookHandler{

    private String secretKey;

    public SecuredWebhookHandler(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public void handle(String id, String signature, String event, String json) throws WebhookException {
        if(secretKey.equals(signature)){
            doHandle(id, event, json);
        }
    }
    
    public abstract void doHandle(String id, String event, String json) throws WebhookException;
    
}
