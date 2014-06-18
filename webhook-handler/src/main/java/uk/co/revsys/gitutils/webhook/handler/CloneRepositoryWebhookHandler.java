package uk.co.revsys.gitutils.webhook.handler;

import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.util.FileUtils;

public class CloneRepositoryWebhookHandler extends SecuredWebhookHandler {

    private String repository;
    private String directory;

    public CloneRepositoryWebhookHandler(String repository, String directory, String secretKey) {
        super(secretKey);
        this.repository = repository;
        this.directory = directory;
    }

    @Override
    public void doHandle(String id, String event, String json) throws WebhookException {
        try {
            File dir = new File(directory);
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