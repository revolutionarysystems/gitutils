package uk.co.revsys.gitutils.webhook.handler;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebhookHandlerServlet extends HttpServlet {

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
            webhookHandler.handle(req.getHeader("X-Github-Delivery"), req.getHeader("X-Hub-Signature"), req.getHeader("X-Github-Event"), IOUtils.toString(req.getInputStream()));
        } catch (WebhookException ex) {
            throw new ServletException(ex);
        }
    }

}
