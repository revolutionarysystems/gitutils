package uk.co.revsys.gitutils.webhook.handler;

import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloneRepositoryWebhookHandler implements WebhookHandler {

    Logger LOGGER = LoggerFactory.getLogger(CloneRepositoryWebhookHandler.class);

    private String repository;
    private String directory;

    public CloneRepositoryWebhookHandler(String repository, String directory) {
        this.repository = repository;
        this.directory = directory;
    }

    @Override
    public void handle(String id, String event, String json) throws WebhookException {
        try {
            File dir = new File(directory);
            LOGGER.debug("Cloning " + repository + " into " + dir.getAbsolutePath());
            FileUtils.delete(dir);
            FileUtils.mkdirs(dir);
            Git.cloneRepository().setURI(repository).setDirectory(dir).call();
        } catch (GitAPIException ex) {
            throw new WebhookException(ex);
        } catch (IOException ex) {
            throw new WebhookException(ex);
        }
    }
}
