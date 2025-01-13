package servlet;

import dao.Category;
import dao.Vente;
import dao.VenteDetails;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class VenteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");

            if ("list".equals(action)) {
                // Récupérer la liste des ventes
                ArrayList<String[]> salesList = Vente.getSalesList();
                req.setAttribute("liste_ventes", salesList);
                req.setAttribute("pageTitle", "Liste des Ventes");
                // Retrieve all categories for the filter dropdown
                ArrayList<Category> categories = Category.all();
                req.setAttribute("categories", categories);
                RequestDispatcher dispatcher = req.getRequestDispatcher("vente.jsp");
                dispatcher.forward(req, resp);
            }

 
            
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
         
        try{    
        String categoryName = req.getParameter("categoryName");
        boolean isNature = Boolean.parseBoolean(req.getParameter("isNature"));

        // Récupérer les ventes filtrées
        ArrayList<String[]> filteredSales = Vente.getFilteredSales(isNature, categoryName);

        // Passer les résultats à la vue
        req.setAttribute("filteredSales", filteredSales);
        req.setAttribute("pageTitle", "Ventes Filtrées");
        ArrayList<Category> categories = Category.all();
        req.setAttribute("categories", categories);
        // Optionally clear the session attribute "filtre" if needed
        req.getSession().removeAttribute("filtre");

        RequestDispatcher dispatcher = req.getRequestDispatcher("vente.jsp");
        dispatcher.forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
