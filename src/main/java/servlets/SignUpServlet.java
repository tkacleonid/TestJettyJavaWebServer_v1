package servlets;

import com.google.gson.Gson;
import model.accounts.AccountService;
import model.accounts.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet{
    private final AccountService accountService;
    private final String USER_ALREADY_EXISTS_MESSAGE = "User this this login already exists";

    public SignUpServlet(AccountService accountService) { this.accountService = accountService; }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        Map<String,Object> pageVariable = new HashMap<>();
        pageVariable.put("message","");
        response.getWriter().println(PageGenerator.instance().getPage("signup.html",pageVariable));
        response.setStatus(HttpServletResponse.SC_OK);
        return;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        if (login == null || pass == null) {
            response.setContentType("text/html; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile profile = accountService.getUserByLogin(login);
        if (profile != null) {
            response.setContentType("text/html; charset=utf-8");
            Map<String,Object> pageVariable = new HashMap<>();
            pageVariable.put("message",USER_ALREADY_EXISTS_MESSAGE);
            response.getWriter().println(PageGenerator.instance().getPage("signup.html",pageVariable));
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        accountService.addNewUser(new UserProfile(login,pass,""));
        //this.getServletContext().getRequestDispatcher("/signin").forward(request,response);
        //response.setContentType("text/html; charset=utf-8");
        //response.setStatus(HttpServletResponse.SC_OK);
    }
}
