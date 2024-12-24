package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.User;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("user");
        resp.sendRedirect("recipe");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String email = req.getParameter("userEmail");
        String password = req.getParameter("userPassword");
        User user = new User(email, password);
        try {
            user.findByEmailAndPassword();
            if (user.getId() == 0) {
                resp.sendRedirect("form-login.jsp?error=true");
            } else {
                req.getSession().setAttribute("user", user);
                resp.sendRedirect("recipe");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}