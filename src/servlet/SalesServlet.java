package servlet;

import dao.Sale;
import dao.SaleRecipe;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SalesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ArrayList<Sale> sales = Sale.all();
            request.setAttribute("sales", sales);
            request.getRequestDispatcher("/sales.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving sales", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idUser = Integer.parseInt(request.getParameter("idUser"));
        LocalDate saleDate = LocalDate.parse(request.getParameter("saleDate"));
        double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));

        Sale sale = new Sale(0, idUser, saleDate, totalAmount);
        try {
            sale.create();
            int saleId = sale.getIdSale();

            int productCount = 0;
            
            while (request.getParameter("idRecipe" + (productCount + 1)) != null) {
                
                productCount++;
                int idRecipe = Integer.parseInt(request.getParameter("idRecipe" + productCount));
                int quantity = Integer.parseInt(request.getParameter("qtt" + productCount));

                SaleRecipe saleRecipe = new SaleRecipe(saleId, idRecipe, quantity);
                saleRecipe.create();
            }

            response.sendRedirect("sales");
        } catch (Exception e) {
            throw new ServletException("Error creating sale", e);
        }
    }
}