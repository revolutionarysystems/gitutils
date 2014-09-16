package uk.co.revsys.gitutils.github.webhook.handler;

import uk.co.revsys.gitutils.github.webhook.handler.filter.WebhookFilter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloneRepositoryWebhookHandler extends FilteredWebhookHandler {

    Logger LOGGER = LoggerFactory.getLogger(CloneRepositoryWebhookHandler.class);

    private final File directory;
    private final String username;
    private final String password;

    public CloneRepositoryWebhookHandler(File directory, String username, String password, WebhookFilter filter) {
        super(filter);
        this.directory = directory;
        this.username = username;
        this.password = password;
    }

    public CloneRepositoryWebhookHandler(File directory, String username, String password, List<WebhookFilter> filters) {
        super(filters);
        this.directory = directory;
        this.username = username;
        this.password = password;
    }

    @Override
    public void handle(String requestId, String repository, String event, JSONObject json) throws WebhookException {
        try {
            LOGGER.debug("Cloning " + repository + " into " + directory.getAbsolutePath());
            if (directory.exists()) {
                FileUtils.forceDelete(directory);
            }
            FileUtils.forceMkdir(directory);
            CloneCommand command = Git.cloneRepository().setURI(repository).setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).setDirectory(directory);
            command.call();
        } catch (GitAPIException ex) {
            throw new WebhookException(ex);
        } catch (IOException ex) {
            throw new WebhookException(ex);
        }
    }
}
