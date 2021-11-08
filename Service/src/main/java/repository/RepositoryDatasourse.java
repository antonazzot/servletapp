package repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class RepositoryDatasourse implements DataSource {
    private static RepositoryDatasourse instance;
    private final String DRIVER;
    private final String URL;
    private final String NAME;
    private final String PASSWWORD;

    private RepositoryDatasourse(String driver, String url, String passwword, String name) {
        this.DRIVER=driver;
        this.NAME=name;
        this.PASSWWORD=passwword;
        this.URL=url;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static RepositoryDatasourse getInstance() {
        if (instance == null) {
            synchronized (RepositoryDatasourse.class) {
                if (instance == null) {
                    Properties properties = new Properties();
                    try {
                        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    instance = new RepositoryDatasourse(
                            properties.getProperty("postgres.driver"),
                            properties.getProperty("postgres.url"),
                            properties.getProperty("postgres.password"),
                            properties.getProperty("postgres.name")
                    );
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
       return DriverManager.getConnection(this.URL,this.NAME, this.PASSWWORD);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
