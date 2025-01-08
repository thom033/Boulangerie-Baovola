package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import java.util.ArrayList;

public class StockIngredient {
    private int idStock;
    private int idIngredient;
    private int idType;
    private int qtt;

    // Getters and Setters
    public int getIdStock() {
        return idStock;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
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

    public static ArrayList<StockIngredient> all() {
        ArrayList<StockIngredient> stockIngredients = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection to the database
            connection = DBConnection.getPostgesConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM stock_ingredient");

            // Iterate through the result set and create StockIngredient objects
            while (resultSet.next()) {
                StockIngredient stockIngredient = new StockIngredient();
                stockIngredient.setIdStock(resultSet.getInt("id_stock"));
                stockIngredient.setIdIngredient(resultSet.getInt("id_ingredient"));
                stockIngredient.setIdType(resultSet.getInt("id_type"));
                stockIngredient.setQtt(resultSet.getInt("qtt"));
                stockIngredients.add(stockIngredient);
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

        return stockIngredients;
    }

    // Function to create a new StockIngredient record
    public void create() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Establish connection to the database
            connection = DBConnection.getPostgesConnection();
            String sql = "INSERT INTO stock_ingredient (id_ingredient, id_type, qtt) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, this.idIngredient);
            statement.setInt(2, this.idType);
            statement.setInt(3, this.qtt);
            statement.executeUpdate();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
}