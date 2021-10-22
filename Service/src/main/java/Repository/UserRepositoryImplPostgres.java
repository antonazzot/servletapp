package Repository;

import Repository.ThreadModelRep.ThreadRepositoryImpl;
import ThreadModel.Theams;
import Users.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
    public HashMap <Integer, UserImpl> allUser() {
        HashMap <Integer, UserImpl> users = new HashMap<>();
        try {
            Connection connection = datasourse.getConnection();
           PreparedStatement ps =  connection.prepareStatement("select * from users;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserImpl user = new UserImpl();
                Integer userId =  rs.getInt("id");
                users.put( userId ,
                        user
                                .withId(userId)
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
    public HashMap<Integer, UserImpl> allTrainer() {
        HashMap <Integer, UserImpl> trainers = new HashMap<>();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps =  connection.prepareStatement("select * from users where role_id=2;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Trainer trainer = new Trainer();
                Integer trainerId = rs.getInt("id");
                trainers.put( trainerId,
                        (Trainer) trainer
                                .withId(trainerId)
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
        return trainers;
    }

    @Override
    public HashMap<Integer, UserImpl> allStudent() {
        HashMap<Integer, UserImpl> students = new HashMap<>();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps =  connection.prepareStatement("select * from users where role_id=3;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                Integer studentId = rs.getInt("id");
                students.put(studentId,
                        (Student) student
                                .withId(studentId)
                                .withLogin(rs.getString("login"))
                                .withPassword(rs.getString("password"))
                                .withName(rs.getString("name"))
                                .withAge(rs.getInt("age"))
                                .withRole(checkRole(rs.getInt("role_id")))



                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public HashMap<Integer, UserImpl> allAdmin() {
        HashMap<Integer, UserImpl> administrators = new HashMap<>();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps =  connection.prepareStatement("select * from users where role_id=3;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Administrator administrator = new Administrator();
                Integer adminId = rs.getInt("id");
                administrators.put(adminId,
                        (Administrator) administrator
                                .withId(adminId)
                                .withLogin(rs.getString("login"))
                                .withPassword(rs.getString("password"))
                                .withName(rs.getString("name"))
                                .withAge(rs.getInt("age"))
                                .withRole(checkRole(rs.getInt("role_id")))

                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administrators;
    }

    @Override
    public UserImpl getUserById(Integer id) {
        UserImpl user =  new UserImpl();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users where id="+"(?)");
            ps.setInt(1, id);
            ResultSet rs =ps.executeQuery();
            while (rs.next()){
                user = user
                        .withId(rs.getInt("id"))
                        .withLogin(rs.getString("login"))
                        .withPassword(rs.getString("password"))
                        .withName(rs.getString("name"))
                        .withAge(rs.getInt("age"))
                        .withRole(checkRole(rs.getInt("role_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
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
        UserImpl user = getUserById(id);
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM users where id =" + "(?)");

            ps.setInt(1, id);
            ps.executeUpdate();
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }
    @Override
    public HashMap<Integer, UserImpl> freeTrainer() {
        HashMap <Integer, UserImpl> result = new HashMap<>();
        ArrayList <Integer> busyTrainerId = new ArrayList<>();
        if (ThreadRepositoryImpl.getInstance().allGroup().isEmpty())
            return  allTrainer();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps =  connection.prepareStatement("select trainer_id" +
                    " from group");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                busyTrainerId.add(rs.getInt("trainer_id"));
            }
            for (UserImpl trainer:
            allTrainer().values()) {
                for (int i = 0; i < busyTrainerId.size(); i++) {
                    if (trainer.getId() != busyTrainerId.get(i))
                        result.put(trainer.getId(), trainer);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }



}
