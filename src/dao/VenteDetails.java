package dao;

import java.sql.*;
import java.util.ArrayList;

public class VenteDetails {
    private int venteId;
    private int recipeId;
    private int quantity;
    private double unitPrice;

    // Constructor
    public VenteDetails(int venteId, int recipeId, int quantity, double unitPrice) {
        this.venteId = venteId;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public VenteDetails() {}

    // Getters and setters
    public int getVenteId() { return venteId; }
    public void setVenteId(int venteId) { this.venteId = venteId; }

    public int getRecipeId() { return recipeId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    // Method to fetch all vente details
    public static ArrayList<VenteDetails> all() throws Exception {
        ArrayList<VenteDetails> venteDetails = new ArrayList<>();
        String query = "SELECT * FROM vente_details";
        
        try (Connection connection = DBConnection.getPostgesConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                int venteId = resultSet.getInt("id_vente");
                int recipeId = resultSet.getInt("id_recipe");
                int quantity = resultSet.getInt("quantity");
                double unitPrice = resultSet.getDouble("unit_price");
                
                venteDetails.add(new VenteDetails(venteId, recipeId, quantity, unitPrice));
            }
        }
        
        return venteDetails;
    }

    // Method to create a vente detail
    public void create() throws Exception {
        String query = "INSERT INTO vente_details(id_vente, id_recipe, quantity, unit_price) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = DBConnection.getPostgesConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, venteId);
            statement.setInt(2, recipeId);
            statement.setInt(3, quantity);
            statement.setDouble(4, unitPrice);
            statement.executeUpdate();
        }
    }

    // Method to update a vente detail
    public void update() throws Exception {
        String query = "UPDATE vente_details SET quantity = ?, unit_price = ? WHERE id_vente = ? AND id_recipe = ?";
        
        try (Connection connection = DBConnection.getPostgesConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, quantity);
            statement.setDouble(2, unitPrice);
            statement.setInt(3, venteId);
            statement.setInt(4, recipeId);
            statement.executeUpdate();
        }
    }

    // Method to delete a vente detail
    public void delete() throws Exception {
        String query = "DELETE FROM vente_details WHERE id_vente = ? AND id_recipe = ?";
        
        try (Connection connection = DBConnection.getPostgesConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, venteId);
            statement.setInt(2, recipeId);
            statement.executeUpdate();
        }
    }
}
