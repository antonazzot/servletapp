package Repository.DAO;
import Users.UserImpl;
import org.postgresql.Driver;
import java.sql.*;

public class DataBaseConnection {
    public static final String url = "jdbc:postgresql://localhost:5432/WEBDB";
    public static final String driver = "org.postgresql.Driver";
    public static final String pass = "29297929";
    public static final String name = "postgres";
    public static Connection connection = getStatement();


    public static void create (UserImpl user) {
        Statement statement;
        try {
            PreparedStatement ps = connection.prepareStatement("insert into  public.users values (?,?,?,?,?)");
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getAge());

            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private static Connection getStatement() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,name, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }









}
