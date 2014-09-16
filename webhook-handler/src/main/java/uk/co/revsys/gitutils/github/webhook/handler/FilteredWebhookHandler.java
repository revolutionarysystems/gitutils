package uk.co.revsys.gitutils.github.webhook.handler;

import uk.co.revsys.gitutils.github.webhook.handler.filter.WebhookFilter;
import java.util.LinkedList;
import java.util.List;

public abstract class FilteredWebhookHandler implements WebhookHandler {

    private final List<WebhookFilter> filters;

    public FilteredWebhookHandler(WebhookFilter filter) {
        filters = new LinkedList();
        filters.add(filter);
    }

    public FilteredWebhookHandler(List<WebhookFilter> filters) {
        this.filters = filters;
    }

    @Override
    public boolean canHandle(String repository, String event) {
        for (WebhookFilter filter : filters) {
            if (!filter.canHandle(repository, event)) {
                return false;
            }
        }
        return true;
    }

}
