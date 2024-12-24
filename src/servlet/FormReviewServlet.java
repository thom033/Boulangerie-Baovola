package servlet;

import java.io.IOException;
import java.util.ArrayList;

import dao.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.Recipe;
import dao.Review;
import util.SessionUtils;

public class FormReviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtils.isUserConnected(req)) {
            resp.sendRedirect("form-login");
            return;
        }

        String action = req.getParameter("action");
        User connectedUser = SessionUtils.getConnectedUser(req);
        Review review = new Review();
        ArrayList<Recipe> recipes;

        try {
            recipes = Recipe.all();
        } catch (Exception e) {
            throw new ServletException(e);
        }

        if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            review.setId(id);
            try {
                review.find();
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } else {
            action = "create";
            review.setIdUser(connectedUser.getId());
        }

        if (review.getIdUser() != connectedUser.getId()) {
            resp.sendRedirect("review");
            return;
        }

        req.setAttribute("action", action);
        req.setAttribute("review", review);
        req.setAttribute("recipes", recipes);
        req.setAttribute("activeMenuItem", "review");
        req.setAttribute("pageTitle", "Retour");

        RequestDispatcher dispatcher = req.getRequestDispatcher("form-review.jsp");
        dispatcher.forward(req, resp);
    }

}