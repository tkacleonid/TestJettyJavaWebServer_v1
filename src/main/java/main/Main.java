package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.DemoServlet;


public class Main {
    public static void main(String [] args) throws Exception {

        DemoServlet frontend = new DemoServlet();


        Server server = new Server(8080);
        server.start();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);
        context.addServlet(new ServletHolder(frontend),"/*");

        server.start();
        server.join();


    }
}
