package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private static final Properties properties = new Properties();
    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load PostgreSQL driver", e);
        }
        
        // Spécifiez le chemin absolu ou relatif du fichier db.properties
        String propertiesPath = "e:/S5/Boulangerie-Baovola/src/main/ressources/db.properties";

        try (InputStream input = new FileInputStream(propertiesPath)) {
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load db.properties", ex);
        }

        String postgres_hote = properties.getProperty("postgres_hote");
        String postgres_port = properties.getProperty("postgres_port");
        String postgres_bdd = properties.getProperty("postgres_bdd");
        String postgres_utilisateur = properties.getProperty("postgres_utilisateur");
        String postgres_mdp = properties.getProperty("postgres_mdp");
        String postgresql_url = "jdbc:postgresql://" + postgres_hote + ":" + postgres_port + "/" + postgres_bdd;

        config.setJdbcUrl(postgresql_url);
        config.setUsername(postgres_utilisateur);
        config.setPassword(postgres_mdp);
        config.addDataSourceProperty("cachePrepStmts", properties.getProperty("cachePrepStmts"));
        // Ajoutez d'autres propriétés de configuration si nécessaire

        ds = new HikariDataSource(config);
    }

    public static Connection getPostgesConnection() throws SQLException {
        return ds.getConnection();
    }
}