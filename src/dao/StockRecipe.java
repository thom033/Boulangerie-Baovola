package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StockRecipe {
    private int idStock;
    private int idRecipe;
    private int idType;
    private int qtt;

    // Getters and Setters
    public int getIdStock() {
        return idStock;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getQtt() {
        return qtt;
    }

    public void setQtt(int qtt) {
        this.qtt = qtt;
    }

    public StockRecipe() {}

    // Function to get all records from stock_recipe
    public static ArrayList<StockRecipe> all() {
        ArrayList<StockRecipe> stockRecipes = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection to the database
            connection = DBConnection.getPostgesConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM stock_recipe");

            // Iterate through the result set and create StockRecipe objects
            while (resultSet.next()) {
                StockRecipe stockRecipe = new StockRecipe();
                stockRecipe.setIdStock(resultSet.getInt("id_stock"));
                stockRecipe.setIdRecipe(resultSet.getInt("id_recipe"));
                stockRecipe.setIdType(resultSet.getInt("id_type"));
                stockRecipe.setQtt(resultSet.getInt("qtt"));
                stockRecipes.add(stockRecipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return stockRecipes;
    }


}