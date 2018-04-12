package main;

import model.accounts.AccountService;
import model.accounts.UserProfile;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;


public class Main {
    public static void main(String [] args) throws Exception {

       // DemoServlet frontend = new DemoServlet();

        AccountService accountService = new AccountService();

        //accountService.addNewUser(new UserProfile("admin"));
        //accountService.addNewUser(new UserProfile("test"));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new UsersServlet(accountService)),"/users");
        context.addServlet(new ServletHolder(new SessionsServlet(accountService)),"/sessions");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)),"/signup");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)),"/signin");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler,context});



        Server server = new Server(8080);

        server.setHandler(handlers);

        server.start();

        System.out.println("Server started");

        server.join();


    }
}
