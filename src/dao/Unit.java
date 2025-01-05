package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Unit {
    private int id_unit;
    private String unit_name;

    public Unit() {}

    public Unit(int id_unit) {
        this.id_unit = id_unit;
    }

    public Unit(String unit_name) {
        this.unit_name = unit_name;
    }

    public Unit(int id_unit, String unit_name) {
        this.id_unit = id_unit;
        this.unit_name = unit_name;
    }

    public int getId_unit() {
        return id_unit;
    }

    public void setId_unit(int id_unit) {
        this.id_unit = id_unit;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public void save() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getPostgesConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "INSERT INTO unit (unit_name)"
                + " VALUES (?)"
            );
            statement.setString(1, unit_name);
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
                "UPDATE unit"
                + " SET unit_name = ?"
                + " WHERE id_unit = ?"
            );
            statement.setString(1, unit_name);
            statement.setInt(2, id_unit);
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
            statement = connection.prepareStatement(
                "DELETE FROM unit"
                + " WHERE id_unit = ?"
            );
            statement.setInt(1, id_unit);
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

    public static List<Unit> getAll() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Unit> units = new ArrayList<>();
        try {
            connection = DBConnection.getPostgesConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM unit"
            );
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Unit unit = new Unit();
                unit.setId_unit(resultSet.getInt("id_unit"));
                unit.setUnit_name(resultSet.getString("unit_name"));
                units.add(unit);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return units;
    }

    public static Unit getById(int id_unit) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Unit unit = new Unit();
        try {
            connection = DBConnection.getPostgesConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM unit"
                + " WHERE id_unit = ?"
            );
            statement.setInt(1, id_unit);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                unit.setId_unit(resultSet.getInt("id_unit"));
                unit.setUnit_name(resultSet.getString("unit_name"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return unit;
    }

    public static Unit getByName(String unit_name) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Unit unit = new Unit();
        try {
            connection = DBConnection.getPostgesConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM unit"
                + " WHERE unit_name = ?"
            );
            statement.setString(1, unit_name);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                unit.setId_unit(resultSet.getInt("id_unit"));
                unit.setUnit_name(resultSet.getString("unit_name"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return unit;
    }

    public static List<Unit> search(String keyword) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Unit> units = new ArrayList<>();
        try {
            connection = DBConnection.getPostgesConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM unit"
                + " WHERE unit_name ILIKE ?"
            );
            statement.setString(1, "%" + keyword + "%");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Unit unit = new Unit();
                unit.setId_unit(resultSet.getInt("id_unit"));
                unit.setUnit_name(resultSet.getString("unit_name"));
                units.add(unit);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return units;
    }
}
