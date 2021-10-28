package Repository;

import Repository.ThreadModelRep.ThreadRepositoryImpl;
import ThreadModel.Theams;
import Users.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
@Slf4j
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
        try (Connection connection = datasourse.getConnection()){
           PreparedStatement ps =  connection.prepareStatement("select * from users;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Integer userId =  rs.getInt("id");
                users.put( userId ,
                        new UserImpl()
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
        try (Connection connection = datasourse.getConnection()){
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
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps =  connection.prepareStatement("select * from users where role_id=3;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                Integer studentId = rs.getInt("id");
                students.put(studentId,
                        (Student) student
                                .withTheamMark(new HashMap<>())
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
        try (Connection connection = datasourse.getConnection()){
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
        try (Connection connection = datasourse.getConnection()){
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
    public int  saveUser(UserImpl user) {
       int userId =0;
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (name, login, password, age, role_id) " +
                    "Values (?, ?, ?, ?, ?) returning id");

            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getAge());
            ps.setInt(5, userGetRoleForDB(user.getRole()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
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
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        // cascade delete for entity
        if (entity.equals("student") || entity.equals("trainer") || entity.equals("administrator")){
            entity = "user";
        }
        try (Connection connection = datasourse.getConnection()){
            switch (entity) {
                case "user":
                {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM users where id = ?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    ps.close();
                } break;
                case "group":
                {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM student_group where group_id = ?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    ps.close();
                    PreparedStatement ps1 = connection.prepareStatement("DELETE FROM theam_group where group_id = ?");
                    ps1.setInt(1, id);
                    ps1.executeUpdate();
                    ps1.close();
                    PreparedStatement ps2 = connection.prepareStatement("DELETE FROM group where id = ?");
                    ps2.setInt(1, id);
                    ps2.executeUpdate();
                    ps2.close();
                } break;
                case "theam":
                {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM mark where theam_id = ?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    ps.close();
                    PreparedStatement ps1 = connection.prepareStatement("DELETE FROM theam_group where theam_id = ?");
                    ps1.setInt(1, id);
                    ps1.executeUpdate();
                    ps1.close();
                    PreparedStatement ps2 = connection.prepareStatement("DELETE FROM theam where id = ?");
                    ps2.setInt(1, id);
                    ps2.executeUpdate();
                    ps2.close();
                } break;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return null;
    }

    @Override
    public UserImpl updateUser(UserImpl user) {

        return null;
    }

    @Override
    public HashMap<Integer, UserImpl> freeTrainer() {

        if (ThreadRepositoryImpl.getInstance().allGroup().isEmpty())
            return  allTrainer();
        else {
            try (Connection connection = datasourse.getConnection()){
                ArrayList <UserImpl> busyTrainer = new ArrayList<>();
                PreparedStatement ps = connection.prepareStatement("select *" +
                        "from \"group\"");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    busyTrainer.add(getUserById(rs.getInt("trainer_id")));
                }
              return freeTrainerexecute(busyTrainer);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public ArrayList <UserImpl> studentFromGroup(Integer groupId) {
        ArrayList <UserImpl> result =  new ArrayList<>();
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = connection.prepareStatement("select *" +
                    "from student_group where group_id = ?");
            ps.setInt(1, groupId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserImpl user = getUserById(rs.getInt("student_id"));
                result.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public UserImpl getUserByParam(String name, String login, String pass, int age) {
        UserImpl user = new UserImpl();
        log.info("Get userByPARAM = {}", name, login, pass, age);
        try (Connection connection = datasourse.getConnection()){
            log.info("in try block", name, login, pass, age);
            PreparedStatement ps = connection.prepareStatement(
                    "select * from users " +
                            " where \"name\" = "   +  name +
                            " and \"login\" = " + login +
                            " and \"password\" = " + pass +
                            " and \"age\" = " + age
                            );

            ResultSet rs =ps.executeQuery();
            log.info("After ps block");
            while (rs.next()){
                user = user
                        .withId(rs.getInt("id"))
                        .withLogin(rs.getString("login"))
                        .withPassword(rs.getString("password"))
                        .withName(rs.getString("name"))
                        .withAge(rs.getInt("age"))
                        .withRole(checkRole(rs.getInt("role_id")));
                log.info("after while");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    private HashMap <Integer, UserImpl> freeTrainerexecute(ArrayList<UserImpl> busyTrainer) {
        HashMap <Integer, UserImpl> result =  new HashMap<>(allTrainer());
        for (UserImpl alltrainer :
                allTrainer().values()) {
        for (UserImpl busyTr : busyTrainer) {
                  if (alltrainer.getId() == busyTr.getId())
                    result.remove(busyTr.getId());
            }
        }
        return result;
    }


}
