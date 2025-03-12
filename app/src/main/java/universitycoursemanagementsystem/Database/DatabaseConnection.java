package universitycoursemanagementsystem.Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The DatabaseConnection class provides a connection pool to the PostgreSQL database
 * using HikariCP for efficient connection management.
 * 
 * <p>This class initializes the connection pool with specified configurations such as
 * JDBC URL, username, password, maximum pool size, minimum idle connections, idle timeout,
 * connection timeout, leak detection threshold, and auto-commit settings.</p>
 * 
 * <p>Usage:</p>
 * <pre>
 * {@code
 * try (Connection connection = DatabaseConnection.getConnection()) {
 *     // Use the connection
 * } catch (SQLException e) {
 *     e.printStackTrace();
 * }
 * }
 * </pre>
 */
public class DatabaseConnection {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://46.96.32.5:5432/university_database");
        config.setUsername("ian-muyala");
        config.setPassword("BABY_rhine32");
        config.setMaximumPoolSize(10);  // Max number of connections in the pool
        config.setMinimumIdle(2);      // Minimum number of idle connections
        config.setIdleTimeout(30000);  // Time before idle connections are removed (30 seconds)
        config.setConnectionTimeout(10000); // Timeout for getting a connection (10 seconds)
        config.setLeakDetectionThreshold(60000); // Detect leaked connections (60 seconds)
        config.setAutoCommit(true);   // Disable auto-commit for transaction control
        config.setMaxLifetime(1800000); // Maximum lifetime of a connection (30 minutes)
        config.setValidationTimeout(5000); // Timeout for connection validation (5 seconds)
        config.setConnectionTestQuery("SELECT 1"); // Test query for connection validation

        dataSource = new HikariDataSource(config);

        // Add a shutdown hook to close the data source when the JVM shuts down
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (dataSource != null && !dataSource.isClosed()) {
                dataSource.close();
                System.out.println("HikariCP connection pool closed.");
            }
        }));
    }

    /**
     * Retrieves a connection from the connection pool.
     *
     * @return a Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Closes the HikariCP data source and releases all resources.
     * This method should only be called when the application is shutting down.
     */
    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("HikariCP connection pool closed.");
        }
    }
}