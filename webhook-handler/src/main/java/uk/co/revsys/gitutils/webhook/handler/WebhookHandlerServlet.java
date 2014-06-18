package uk.co.revsys.gitutils.webhook.handler;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebhookHandlerServlet extends HttpServlet {

    Logger LOGGER = LoggerFactory.getLogger(WebhookHandlerServlet.class);
    
    private WebhookHandler webhookHandler;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        webhookHandler = webApplicationContext.getBean(WebhookHandler.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String requestId = req.getHeader("X-Github-Delivery");
            LOGGER.debug("requestId = " + requestId);
            String event = req.getHeader("X-Github-Event");
            LOGGER.debug("event = " + event);
            String json = IOUtils.toString(req.getInputStream());
            LOGGER.debug("json = " + json);
            webhookHandler.handle(requestId, event, json);
        } catch (WebhookException ex) {
            LOGGER.error("Unable to process webhook", ex);
            throw new ServletException(ex);
        }
    }

}
