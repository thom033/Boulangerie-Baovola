package servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.Recipe;
import dao.Sale;
import util.SessionUtils;

public class FormSaleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtils.isUserConnected(req)) {
            resp.sendRedirect("form-login");
            return;
        }

        String action = req.getParameter("action");
        ArrayList<Sale> sales;
        ArrayList<Recipe> recipes;
        int idSale = req.getParameter("idSale") == null ? 0 : Integer.parseInt(req.getParameter("idSale"));
        Sale sale = new Sale();
        sale.setIdSale(idSale);

        try {
            sales = Sale.all();
            recipes = Recipe.all();
        } catch (Exception e) {
            throw new ServletException(e);
        }

        if (action != null && action.equals("update")) {
            int id = Integer.parseInt(req.getParameter("id"));
            sale.setIdSale(id);
            try {
                sale.find();
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } else {
            action = "create";
        }

        req.setAttribute("action", action);
        req.setAttribute("sale", sale);
        req.setAttribute("sales", sales);
        req.setAttribute("recipes", recipes);
        req.setAttribute("activeMenuItem", "sale");
        req.setAttribute("pageTitle", "Vente");

        RequestDispatcher dispatcher = req.getRequestDispatcher("form-sale.jsp");
        dispatcher.forward(req, resp);
    }
}