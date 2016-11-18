/**
 * Created by djmckee on 03/11/2016.
 */

import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import uk.ac.ncl.csc3422.kennelbooking.DogSize;
import uk.ac.ncl.csc3422.kennelbooking.Kennel;
import uk.ac.ncl.csc3422.kennelbooking.KennelFactory;
import uk.ac.ncl.csc3422.kennelbooking.KennelReport;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class KennelWebServer {
    public static void main(String[] args) throws URISyntaxException {
        //Firstly craete a Kennel object using the factory class method
        Kennel dogHouse = KennelFactory.initialiseKennel();
        //Use the report to see how this object is structured
        System.out.println(KennelReport.generateReport(dogHouse));
        //Check a medium sized dog called Bertie into a pen
        dogHouse.bookPen(DogSize.SMALL, "Bertie");
        dogHouse.bookPen(DogSize.GIANT, "Hulk");
        //Use the report to check the updated status
        System.out.println(KennelReport.generateReport(dogHouse));
        //Check Bertie out
        dogHouse.checkout("Bertie");
        //Look at the report to see he has left
        System.out.println(KennelReport.generateReport(dogHouse));

        Server server = new Server(8080);

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

        ServletHolder holderDefault = new ServletHolder("default", DefaultServlet.class);
        ServletHolder indexDefault = new ServletHolder("index", IndexServlet.class);

        context.addServlet(holderDefault, "/");
        context.addServlet(indexDefault, "/index");

        server.setHandler(context);


        try {
            server.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}