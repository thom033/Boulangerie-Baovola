package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Sale {
    private int idSale;
    private int idUser;
    private LocalDate saleDate;
    private double totalAmount;

    public Sale() {
    }

    public Sale(int idSale, int idUser, LocalDate saleDate, double totalAmount) {
        this.idSale = idSale;
        this.idUser = idUser;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
    }

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public static ArrayList<Sale> search(int searchIdUser, LocalDate minSaleDate, LocalDate maxSaleDate, double minTotalAmount, double maxTotalAmount) throws Exception {
        ArrayList<Sale> sales = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getPostgesConnection();

            StringBuilder sql = new StringBuilder("SELECT * FROM sale WHERE 1=1");
            if (searchIdUser != 0) {
                sql.append(" AND id_user = ?");
            }
            if (minSaleDate != null) {
                sql.append(" AND sale_date >= ?");
            }
            if (maxSaleDate != null) {
                sql.append(" AND sale_date <= ?");
            }
            if (minTotalAmount != 0) {
                sql.append(" AND total_amount >= ?");
            }
            if (maxTotalAmount != 0) {
                sql.append(" AND total_amount <= ?");
            }

            statement = connection.prepareStatement(sql.toString());

            int paramIndex = 1;
            if (searchIdUser != 0) {
                statement.setInt(paramIndex++, searchIdUser);
            }
            if (minSaleDate != null) {
                statement.setDate(paramIndex++, Date.valueOf(minSaleDate));
            }
            if (maxSaleDate != null) {
                statement.setDate(paramIndex++, Date.valueOf(maxSaleDate));
            }
            if (minTotalAmount != 0) {
                statement.setDouble(paramIndex++, minTotalAmount);
            }
            if (maxTotalAmount != 0) {
                statement.setDouble(paramIndex++, maxTotalAmount);
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idSale = resultSet.getInt("id_sale");
                int idUser = resultSet.getInt("id_user");
                LocalDate saleDate = resultSet.getDate("sale_date").toLocalDate();
                double totalAmount = resultSet.getDouble("total_amount");

                sales.add(new Sale(idSale, idUser, saleDate, totalAmount));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return sales;
    }

    public static ArrayList<Sale> all() throws Exception {
        ArrayList<Sale> sales = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getPostgesConnection();
            statement = connection.prepareStatement("SELECT * FROM sale");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idSale = resultSet.getInt("id_sale");
                int idUser = resultSet.getInt("id_user");
                LocalDate saleDate = resultSet.getDate("sale_date").toLocalDate();
                double totalAmount = resultSet.getDouble("total_amount");

                sales.add(new Sale(idSale, idUser, saleDate, totalAmount));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return sales;
    }

    public void find() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getPostgesConnection();
            statement = connection.prepareStatement("SELECT * FROM sale WHERE id_sale = ?");
            statement.setInt(1, idSale);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                idSale = resultSet.getInt("id_sale");
                idUser = resultSet.getInt("id_user");
                saleDate = resultSet.getDate("sale_date").toLocalDate();
                totalAmount = resultSet.getDouble("total_amount");
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public void create() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getPostgesConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "INSERT INTO sale(id_user, sale_date, total_amount) VALUES (?, ?, ?)"
            );
            statement.setInt(1, idUser);
            statement.setDate(2, Date.valueOf(saleDate));
            statement.setDouble(3, totalAmount);
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

    public void update() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getPostgesConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "UPDATE sale SET id_user = ?, sale_date = ?, total_amount = ? WHERE id_sale = ?"
            );
            statement.setInt(1, idUser);
            statement.setDate(2, Date.valueOf(saleDate));
            statement.setDouble(3, totalAmount);
            statement.setInt(4, idSale);
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

    public void delete() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getPostgesConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("DELETE FROM sale WHERE id_sale = ?");
            statement.setInt(1, idSale);
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