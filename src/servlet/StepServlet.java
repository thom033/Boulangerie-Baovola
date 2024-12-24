package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Recipe;
import dao.Step;

public class StepServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");

            if (action != null && action.equals("delete")) {
                int id = Integer.parseInt(req.getParameter("id"));
                Step step = new Step(id);
                step.delete();
            }

            ArrayList<Recipe> recipes = Recipe.all();

            int idRecipe = req.getParameter("searchIdRecipe") == null ? 0 : Integer.parseInt(req.getParameter("searchIdRecipe"));
            String minStepNumberStr = req.getParameter("searchMinStepNumber");
            String maxStepNumberStr = req.getParameter("searchMaxStepNumber");
            String instruction = req.getParameter("searchInstruction") == null ? "" : req.getParameter("searchInstruction");
            int minStepNumber = 0;
            int maxStepNumber = 0;

            if (minStepNumberStr != null && !minStepNumberStr.equals("")) {
                minStepNumber = Integer.parseInt(minStepNumberStr);
            }

            if (maxStepNumberStr != null && !maxStepNumberStr.equals("")) {
                maxStepNumber = Integer.parseInt(maxStepNumberStr);
            }

            ArrayList<Step> steps = Step.search(idRecipe, minStepNumber, maxStepNumber, instruction);

            req.setAttribute("steps", steps);
            req.setAttribute("recipes", recipes);
            req.setAttribute("activeMenuItem", "step");
            req.setAttribute("pageTitle", "Etape");

            RequestDispatcher dispatcher = req.getRequestDispatcher("step.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("idStep"));
        int idRecipe = Integer.parseInt(req.getParameter("stepIdRecipe"));
        int number = Integer.parseInt(req.getParameter("stepNumber"));
        String instruction = req.getParameter("stepInstruction");
        Step step = new Step(id, idRecipe, number, instruction);

        try {
            if (action != null && action.equals("update")) {
                step.update();
            } else {
                step.create();
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

        resp.sendRedirect("step");
    }

}