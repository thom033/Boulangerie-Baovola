package servlet;

import dao.StockIngredient;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import util.SessionUtils;

public class AddIngredientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtils.isUserConnected(req)) {
            resp.sendRedirect("form-login");
            return;
        }

        req.setAttribute("pageTitle", "Ajouter un Ingrédient");
        RequestDispatcher dispatcher = req.getRequestDispatcher("form-add-ingredient.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtils.isUserConnected(req)) {
            resp.sendRedirect("form-login");
            return;
        }

        int idIngredient = Integer.parseInt(req.getParameter("idIngredient"));
        int idType = Integer.parseInt(req.getParameter("idType"));
        int qtt = Integer.parseInt(req.getParameter("qtt"));

        StockIngredient stockIngredient = new StockIngredient();
        // stockIngredient.setIdStock(idStock);
        stockIngredient.setIdIngredient(idIngredient);
        stockIngredient.setIdType(idType);
        stockIngredient.setQtt(qtt);

        try {
            stockIngredient.create();
            resp.sendRedirect("ingredient-list");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}