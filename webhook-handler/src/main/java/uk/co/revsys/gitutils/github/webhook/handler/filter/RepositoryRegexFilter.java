package uk.co.revsys.gitutils.github.webhook.handler.filter;

import java.util.regex.Pattern;

public class RepositoryRegexFilter implements WebhookFilter{

    private final Pattern pattern;

    public RepositoryRegexFilter(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }
    
    @Override
    public boolean canHandle(String repository, String event) {
        return pattern.matcher(repository).matches();
    }

}
