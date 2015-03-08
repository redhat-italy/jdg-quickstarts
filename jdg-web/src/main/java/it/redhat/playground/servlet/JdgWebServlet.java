package it.redhat.playground.servlet;

import it.redhat.playground.application.JdgWebConfiguration;
import it.redhat.playground.configuration.PlaygroundConfiguration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by samuele on 2/25/15.
 */
public class JdgWebServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();

        JdgWebConfiguration config = new JdgWebConfiguration();
        config.configure();
        config.saveCache();
        config.configTwitter();

    }
}
