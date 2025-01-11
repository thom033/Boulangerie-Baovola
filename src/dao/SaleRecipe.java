package dao;

import java.sql.*;
import java.util.ArrayList;

public class SaleRecipe {
    private int idSale;
    private int idRecipe;
    private int qtt;

    // Constructors
    public SaleRecipe() {
    }

    public SaleRecipe(int idSale, int idRecipe, int qtt) {
        this.idSale = idSale;
        this.idRecipe = idRecipe;
        this.qtt = qtt;
    }

    

    // Getters and Setters
    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public int getQtt() {
        return qtt;
    }

    public void setQtt(int qtt) {
        this.qtt = qtt;
    }

    // Search method
    public static ArrayList<SaleRecipe> search(int searchIdSale, int searchIdRecipe, int searchQtt) throws Exception {
        ArrayList<SaleRecipe> saleRecipes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getPostgesConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM sale_recipe WHERE 1=1");

            if (searchIdSale != 0) {
                sql.append(" AND id_sale = ?");
            }
            if (searchIdRecipe != 0) {
                sql.append(" AND id_recipe = ?");
            }
            if (searchQtt != 0) {
                sql.append(" AND qtt = ?");
            }

            statement = connection.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (searchIdSale != 0) {
                statement.setInt(paramIndex++, searchIdSale);
            }
            if (searchIdRecipe != 0) {
                statement.setInt(paramIndex++, searchIdRecipe);
            }
            if (searchQtt != 0) {
                statement.setInt(paramIndex++, searchQtt);
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SaleRecipe saleRecipe = new SaleRecipe();
                saleRecipe.setIdSale(resultSet.getInt("id_sale"));
                saleRecipe.setIdRecipe(resultSet.getInt("id_recipe"));
                saleRecipe.setQtt(resultSet.getInt("qtt"));
                saleRecipes.add(saleRecipe);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return saleRecipes;
    }

    // All method
    public static ArrayList<SaleRecipe> all() throws Exception {
        ArrayList<SaleRecipe> saleRecipes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getPostgesConnection();
            statement = connection.prepareStatement("SELECT * FROM sale_recipe");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SaleRecipe saleRecipe = new SaleRecipe();
                saleRecipe.setIdSale(resultSet.getInt("id_sale"));
                saleRecipe.setIdRecipe(resultSet.getInt("id_recipe"));
                saleRecipe.setQtt(resultSet.getInt("qtt"));
                saleRecipes.add(saleRecipe);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return saleRecipes;
    }

    // Find method
    public void find() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getPostgesConnection();
            statement = connection.prepareStatement("SELECT * FROM sale_recipe WHERE id_sale = ? AND id_recipe = ?");
            statement.setInt(1, idSale);
            statement.setInt(2, idRecipe);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                this.qtt = resultSet.getInt("qtt");
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // Create method
    public void create() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getPostgesConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("INSERT INTO sale_recipe (id_sale, id_recipe, qtt) VALUES (?, ?, ?)");
            statement.setInt(1, idSale);
            statement.setInt(2, idRecipe);
            statement.setInt(3, qtt);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // Update method
    public void update() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getPostgesConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("UPDATE sale_recipe SET qtt = ? WHERE id_sale = ? AND id_recipe = ?");
            statement.setInt(1, qtt);
            statement.setInt(2, idSale);
            statement.setInt(3, idRecipe);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // Delete method
    public void delete() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getPostgesConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("DELETE FROM sale_recipe WHERE id_sale = ? AND id_recipe = ?");
            statement.setInt(1, idSale);
            statement.setInt(2, idRecipe);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
}