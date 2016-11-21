import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the KennelManager system using the Jetty Web Server and JSP Servlets, for CSC3422.
 * <p>
 * Created by Dylan McKee on 03/11/2016.
 */
public class KennelWebServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws URISyntaxException {
        // Ensure the singleton exists.
        KennelManager kennelManager = KennelManager.getInstance();

        Server server = new Server(PORT);

        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setResourceBase(KennelWebServer.class.getResource("/webapp/").toURI().toASCIIString());
        final ContainerInitializer initializer = new ContainerInitializer(new JettyJasperInitializer(), null);
        List<ContainerInitializer> initializers = new ArrayList<ContainerInitializer>() {{
            add(initializer);
        }};
        context.setAttribute("org.eclipse.jetty.containerInitializers", initializers);
        context.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());
        context.addBean(new ServletContainerInitializersStarter(context), true);

        ServletHolder indexDefault = new ServletHolder("index", IndexServlet.class);
        context.addServlet(indexDefault, "/index");
        context.addServlet(indexDefault, "/");

        server.setHandler(context);


        try {
            server.start();
            System.out.println("KennelWebServer running on http://0.0.0.0:" + PORT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}