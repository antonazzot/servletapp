package Repository.ThreadModelRep;

import Repository.RepositoryDatasourse;
import Repository.RepositoryFactory;
import Repository.UserRepositoryImplPostgres;
import ThreadModel.Group;
import ThreadModel.Mark;
import ThreadModel.Salary;
import ThreadModel.Theams;
import Users.Student;
import Users.Trainer;
import Users.UserImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ThreadRepositoryImpl implements ThreadRepository {
    RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();
    private static ThreadRepositoryImpl instance;


    private ThreadRepositoryImpl () {
    }

    public static ThreadRepositoryImpl getInstance() {
        if (instance == null) {
            synchronized (ThreadRepositoryImpl.class) {
                if (instance == null) {
                    instance = new ThreadRepositoryImpl();
                }
            }
        }
        return instance;
    }
    @Override
    public HashMap<Integer, Group> allGroup() {
        HashMap<Integer, Group> result = new HashMap<>();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from \"group\"");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int groupId = rs.getInt("id");
               UserImpl user =   RepositoryFactory.getRepository()
                        .getUserById(rs.getInt("trainer_id"));
                result.put(groupId,
                        new Group()
                                .withId(groupId)
                                .withName(rs.getString("name"))
                                .withTrainer(user)

                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public HashMap<Integer, Theams> allTheams() {
        HashMap<Integer, Theams> result = new HashMap<>();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from theam");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int theamId = rs.getInt("id");
                result.put(theamId,
                        new Theams()
                                .withValue(rs.getString("theam_name"))

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public HashMap<Trainer, ArrayList<Salary>> trainerSalary() {
        HashMap<Trainer, ArrayList<Salary>> result = new HashMap<>();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from salary");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                result.put((Trainer) RepositoryFactory.getRepository().getUserById(rs.getInt("trainer_id")),
                       new ArrayList<>(
                               List.of(new Salary()
                               .withValue(rs.getInt("salary_value")))
                               )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public HashMap<Student, HashMap<Theams, ArrayList<Mark>>> studentTheamMark() {
        HashMap<Student, HashMap<Theams, ArrayList<Mark>>> studentTheamMarkMap = new HashMap<>();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from salary");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                       studentTheamMarkMap.put(
                        (Student) RepositoryFactory.getRepository().getUserById(rs.getInt("student_id")),
                        new HashMap<>(
                                Map.of(theamById(rs.getInt("theam_id")),
                                        new ArrayList<>(
                                                List.of(new Mark
                                                                (rs.getInt("mark_value"))
                                                        )
                                        )
                                )
                        )

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentTheamMarkMap;
    }

    @Override
    public Theams theamById(Integer id) {
        Theams theams = null;
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from theam where id = (?)");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                theams = new Theams()
                        .withValue(rs.getString("theam_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theams;
    }

    public void addTheam (String theam) {
        if (!allTheams().values().stream().map(Theams::getTheamName)
                .collect(Collectors.toList()).contains(theam))
        {
            try {
                Connection connection = datasourse.getConnection();
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO theam (theam_name) " +
                                "Values (?)"
                );
                ps.setString(1, theam);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void addGroup (List<UserImpl> studentList, List <Integer> theamsIdList, Integer trainerId) {
         int groupId =0;
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO \"group\" (name, trainer_id)" +
                            "Values (?,?)"
            );
            ps.setString(1, trainerId.toString()+"'s_Group");
            ps.setInt(2, trainerId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "select id from \"group\" where trainer_id = "+trainerId
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                groupId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = datasourse.getConnection();
            for (UserImpl student:
                 studentList) {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO student_group (group_id, student_id) " +
                                "Values (?, ?)"
                );
               ps.setInt(1, groupId);
               ps.setInt(2, student.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = datasourse.getConnection();
            for (Integer i:
                    theamsIdList) {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO theam_group (group_id, theam_id) " +
                                "Values (?, ?)"
                );
                ps.setInt(1, groupId);
                ps.setInt(2, i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public HashMap<Integer, Theams> freeTheams() {
        HashMap <Integer, Theams> result = new HashMap<>();
        ArrayList <Integer> busyTheamId = new ArrayList<>();
        if (allGroup().isEmpty()) return allTheams();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps =  connection.prepareStatement("select theam_id" +
                    " from theam_group");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                busyTheamId.add(rs.getInt("theam_id"));
            }
            for (Integer i:
                    allTheams().keySet()) {
                for (int j = 0; j < busyTheamId.size(); j++) {
                    if (i != busyTheamId.get(j))
                        result.put(i, theamById(i));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
