package servlet;

import dao.Recipe;
import dao.RecipeIngredient;
import dao.Step;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int idRecipe = req.getParameter("idRecipe") == null ? 0 : Integer.parseInt(req.getParameter("idRecipe"));

            Recipe recipe = new Recipe(idRecipe);
            recipe.find();
            ArrayList<Step> steps = Step.search(idRecipe, 0, 0, "");
            ArrayList<RecipeIngredient> recipeIngredients = RecipeIngredient.search(idRecipe);

            req.setAttribute("recipe", recipe);
            req.setAttribute("steps", steps);
            req.setAttribute("recipeIngredients", recipeIngredients);
            req.setAttribute("activeMenuItem", "recipe");
            req.setAttribute("pageTitle", "Recette");

            RequestDispatcher dispatcher = req.getRequestDispatcher("recipe-details.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}