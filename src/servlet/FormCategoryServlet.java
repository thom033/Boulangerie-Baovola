package servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Category;
import util.SessionUtils;

public class FormCategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtils.isUserConnected(req)) {
            resp.sendRedirect("form-login");
            return;
        }

        String action = req.getParameter("action");
        Category category = new Category();

        if (action != null && action.equals("update")) {
            int id = Integer.parseInt(req.getParameter("id"));
            category.setId(id);
            try {
                category.find();
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } else {
            action = "create";
        }

        req.setAttribute("action", action);
        req.setAttribute("category", category);
        req.setAttribute("activeMenuItem", "category");
        req.setAttribute("pageTitle", "Catégorie de recette");

        RequestDispatcher dispatcher = req.getRequestDispatcher("form-category.jsp");
        dispatcher.forward(req, resp);
    }

}