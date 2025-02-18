package universitycoursemanagementsystem.Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final HikariConfig config = new HikariConfig();
    
    private static final HikariDataSource dataSource;

    static {
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/MariaDB");
        config.setUsername("postgres");
        config.setPassword("admin");
        config.setMaximumPoolSize(10);  // Max number of connections in the pool
        config.setMinimumIdle(2);        // Minimum number of idle connections
        config.setIdleTimeout(30000);    // Time before idle connections are removed
        config.setConnectionTimeout(30000); // Timeout for getting a connection
        config.setLeakDetectionThreshold(2000); // Detect leaked connections
        config.setAutoCommit(true); // Enable auto-commit

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
