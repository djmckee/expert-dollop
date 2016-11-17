/**
 * Created by djmckee on 03/11/2016.
 */

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyServer {
    public static void main(String[] args){
        Server server = new Server(8080);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{"index.html"});
        resource_handler.setResourceBase("./target/classes/webapp");

        //initialise servlet context handler
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context, new DefaultHandler()});
        server.setHandler(handlers);

        //add servlets with holders to context handler
        ServletHolder indexHolder = new ServletHolder(new IndexServlet());
        context.addServlet(indexHolder, "/index");


        try {
            server.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }




    }
}