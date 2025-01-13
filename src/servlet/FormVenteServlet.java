package servlet;


import dao.Category;
import dao.Recipe;
import dao.Vente;
import dao.VenteDetails;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class FormVenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
                        // Inside your controller, you need to set the recipe list in the request attributes before forwarding the request to the JSP.
            ArrayList<Recipe> recipes = Recipe.all(); // Assuming Recipe.all() returns a list of recipes
            req.setAttribute("recipes", recipes);
            // Afficher le formulaire de saisie de vente
            RequestDispatcher dispatcher = req.getRequestDispatcher("formvente.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
           
                // Récupérer l'ID de l'utilisateur. Si l'utilisateur est nul, on passe null.
                Integer userId = req.getParameter("userId") != null ? Integer.parseInt(req.getParameter("userId")) : 0;
                double totalAmount = 0;  // initialement à 0
                Vente vente = new Vente(userId, totalAmount);

                int venteId = vente.create(); 
                vente.setId(venteId);

                // Insérer les détails de la vente
                String[] recipeIds = req.getParameterValues("recipeId");
                String[] quantities = req.getParameterValues("quantity");
                String[] unitPrices = req.getParameterValues("unitPrice");

                double calculatedTotalAmount = 0;  // Calculer le total dans le servlet

                for (int i = 0; i < recipeIds.length; i++) {
                    int recipeId = Integer.parseInt(recipeIds[i]);
                    int quantity = Integer.parseInt(quantities[i]);
                    double unitPrice = Double.parseDouble(unitPrices[i]);

                    VenteDetails venteDetails = new VenteDetails(venteId, recipeId, quantity, unitPrice);
                    venteDetails.create();  // Insérer un détail de vente

                    // Calculer le total en fonction des détails
                    calculatedTotalAmount += quantity * unitPrice;
                }

                // Mettre à jour le montant total dans la table `vente`
                vente.updateTotalAmount(calculatedTotalAmount);

                resp.sendRedirect("vente?action=list");  // Rediriger vers la liste des ventes
            
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
