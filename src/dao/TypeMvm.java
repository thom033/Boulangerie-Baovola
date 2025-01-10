package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TypeMvm {
    private int idType;
    private String typeName;

    // Default constructor
    public TypeMvm() {}

    // Parameterized constructor
    public TypeMvm(int idType, String typeName) {
        this.idType = idType;
        this.typeName = typeName;
    }

    // Getters and Setters
    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    // toString method
    @Override
    public String toString() {
        return "TypeMvm{" +
                "idType=" + idType +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    public static ArrayList<TypeMvm> all() {
        ArrayList<TypeMvm> typeMvms = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection to the database
            connection = DBConnection.getPostgesConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM type_mvm");

            // Iterate through the result set and create TypeMvm objects
            while (resultSet.next()) {
                TypeMvm typeMvm = new TypeMvm();
                typeMvm.setIdType(resultSet.getInt("id_type"));
                typeMvm.setTypeName(resultSet.getString("type_name"));
                typeMvms.add(typeMvm);
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

        return typeMvms;
    }
}