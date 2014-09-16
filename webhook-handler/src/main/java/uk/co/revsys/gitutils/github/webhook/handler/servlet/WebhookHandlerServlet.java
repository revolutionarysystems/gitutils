package uk.co.revsys.gitutils.github.webhook.handler.servlet;

import uk.co.revsys.gitutils.github.webhook.handler.factory.WebhookHandlerFactory;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import uk.co.revsys.gitutils.github.webhook.handler.WebhookException;
import uk.co.revsys.gitutils.github.webhook.handler.WebhookHandler;

public class WebhookHandlerServlet extends HttpServlet {

    Logger LOGGER = LoggerFactory.getLogger(WebhookHandlerServlet.class);

    private WebhookHandlerFactory webhookHandlerFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        webhookHandlerFactory = webApplicationContext.getBean(WebhookHandlerFactory.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String requestId = req.getHeader("X-Github-Delivery");
            LOGGER.debug("requestId = " + requestId);
            String event = req.getHeader("X-Github-Event");
            LOGGER.debug("event = " + event);
            String jsonString = IOUtils.toString(req.getInputStream());
            LOGGER.debug("json = " + jsonString);
            JSONObject json = new JSONObject(jsonString);
            String repositoryUrl = json.getJSONObject("repository").getString("url");
            List<WebhookHandler> webhookHandlers = webhookHandlerFactory.getWebhookHandlers(repositoryUrl, event);
            if (webhookHandlers.isEmpty()) {
                LOGGER.warn("No webhook handler available for " + event + " event on " + repositoryUrl);
            }
            for (WebhookHandler handler : webhookHandlers) {
                handler.handle(requestId, repositoryUrl, event, json);
            }
        } catch (WebhookException ex) {
            LOGGER.error("Unable to process webhook", ex);
        }
        resp.setStatus(200);
    }

}
