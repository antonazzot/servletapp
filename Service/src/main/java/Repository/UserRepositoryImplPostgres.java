package Repository;

import Users.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryImplPostgres implements UserRepository {

    private static UserRepositoryImplPostgres instance;
    public RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();

    private UserRepositoryImplPostgres () {
    }

    public static UserRepositoryImplPostgres getInstance() {
        if (instance == null) {
            synchronized (UserRepositoryImplPostgres.class) {
                if (instance == null) {
                    instance = new UserRepositoryImplPostgres();
                }
            }
        }
        return instance;
    }


    @Override
    public List<User> allUser() {
        ArrayList <User> users = new ArrayList<>();
        try {
            Connection connection = datasourse.getConnection();
           PreparedStatement ps =  connection.prepareStatement("select * from users;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserImpl user = new UserImpl();
                users.add(
                        user
                                .withId(rs.getInt("id"))
                                .withRole(checkRole(rs.getInt("role_id")))
                                .withLogin(rs.getString("login"))
                                .withPassword(rs.getString("password"))
                                .withName(rs.getString("name"))
                                .withAge(rs.getInt("age"))
                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private Role checkRole(int role_id) {
        switch (role_id){
            case 1: return Role.ADMINISTRATOR;

            case 2: return Role.TRAINER;

            case 3:
                default: return Role.STUDENT;
        }

    }

    @Override
    public List<Trainer> allTrainer() {
        ArrayList <Trainer> trainers = new ArrayList<>();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps =  connection.prepareStatement("select * from users where role_id=2;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Trainer trainer = new Trainer();
                trainers.add(
                        (Trainer) trainer
                                .withId(rs.getInt("id"))
                                .withLogin(rs.getString("login"))
                                .withPassword(rs.getString("password"))
                                .withName(rs.getString("name"))
                                .withAge(rs.getInt("age"))


                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }

    @Override
    public List<Student> allStudent() {
        ArrayList <Student> students = new ArrayList<>();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps =  connection.prepareStatement("select * from users where role_id=3;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                students.add(
                        (Student) student
                                .withId(rs.getInt("id"))
                                .withLogin(rs.getString("login"))
                                .withPassword(rs.getString("password"))
                                .withName(rs.getString("name"))
                                .withAge(rs.getInt("age"))



                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public List<Administrator> allAdmin() {
        ArrayList <Administrator> administrators = new ArrayList<>();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps =  connection.prepareStatement("select * from users where role_id=3;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Administrator administrator = new Administrator();
                administrators.add(
                        (Administrator) administrator
                                .withId(rs.getInt("id"))
                                .withLogin(rs.getString("login"))
                                .withPassword(rs.getString("password"))
                                .withName(rs.getString("name"))
                                .withAge(rs.getInt("age"))

                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administrators;
    }

    @Override
    public Optional<UserImpl> getUserById(Integer id) {
        return Optional.empty();
    }

    @Override
    public UserImpl saveUser(UserImpl user) {
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (name, login, password, age, role_id) " +
                    "Values (?, ?, ?, ?, ?)");

            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getAge());
            ps.setInt(5, userGetRoleForDB(user.getRole()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private int userGetRoleForDB(Role role) {
        if (Role.ADMINISTRATOR.equals(role)) {
            return 1;
        }
        else if (Role.TRAINER.equals(role)) {
            return 2;
        }
        return 3;
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id) {
        return Optional.empty();
    }
}
