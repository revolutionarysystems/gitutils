package uk.co.revsys.gitutils.github.webhook.handler;

public class WebhookException extends Exception{

    public WebhookException() {
    }

    public WebhookException(String message) {
        super(message);
    }

    public WebhookException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebhookException(Throwable cause) {
        super(cause);
    }

}
