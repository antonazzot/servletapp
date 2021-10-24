package Repository.ThreadModelRep;

import Repository.RepositoryDatasourse;
import Repository.RepositoryFactory;
import Repository.UserRepositoryImplPostgres;
import Servlets.WatchServlet;
import ThreadModel.Group;
import ThreadModel.Mark;
import ThreadModel.Salary;
import ThreadModel.Theams;
import Users.Student;
import Users.Trainer;
import Users.UserImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ThreadRepositoryImpl implements ThreadRepository {
    RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();
    private static final Logger log = LoggerFactory.getLogger(ThreadRepositoryImpl.class);
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
                                .withId(theamId)
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
            PreparedStatement ps = connection.prepareStatement("select * from mark");
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
            PreparedStatement ps = connection.prepareStatement("select * from theam where id = " + id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                theams = new Theams()
                        .withId(rs.getInt("id"))
                        .withValue(rs.getString("theam_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theams;
    }

    @Override
    public Set<Theams> theamFromGroup(Integer groupId) {
        Set <Theams> result = new HashSet<>();
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "select theam_id from theam_group where group_id = " + groupId
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(theamById(rs.getInt("theam_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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

        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO \"group\" (name, trainer_id)" +
                            "Values (?,?)"
            );
            ps.setString(1, trainerId.toString()+"'s_Group");
            ps.setInt(2, trainerId);
            ps.executeUpdate();
            int groupId = getGroupId(trainerId);
            insertIntoStudent_Group(groupId, studentList);
            insertIntoTheam_Group(groupId, theamsIdList);


        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    private void insertIntoTheam_Group(int groupId, List<Integer> theamsIdList) {
        try {
            Connection connection = datasourse.getConnection();
            for (Integer i:
                    theamsIdList) {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO theam_group (group_id, theam_id) " +
                                "Values (?,?)"
                );
                ps.setInt(1, groupId);
                ps.setInt(2, i);
                ps.executeUpdate();
                ps.close();
                          }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertIntoStudent_Group(int groupId, List<UserImpl> studentList) {
        try {
            Connection connection = datasourse.getConnection();
            for (UserImpl student:
                    studentList) {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO student_group (group_id, student_id) " +
                                "Values (?,?)"
                );
                ps.setInt(1, groupId);
                ps.setInt(2, student.getId());
                ps.executeUpdate();
                ps.close();
                          }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getGroupId(int trainerId) {
        int  groupId = 0;
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "select id from \"group\" where trainer_id = "+trainerId
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                groupId = rs.getInt("id");
            }
            return groupId;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  groupId;
    }

    public HashMap<Integer, Theams> freeTheams() {
        HashMap <Integer,Theams> busyTheam = getBuzyTeam();
        HashMap <Integer,Theams> freeTh = new HashMap<>(allTheams());
        log.info("free Start {}" , busyTheam.values());
        if (allGroup().isEmpty())
            {
            return allTheams();}
        else {
            log.info("in for {}" , busyTheam.values());
                for (Theams allTh:
                    allTheams().values()) {
                    for (Theams bTh:
                         busyTheam.values()) {
                        if (allTh.getId() == bTh.getId() ) {
                           freeTh.remove(allTh.getId());
                        }
                    }

                }

            return freeTh;
       }
//
    }

    private HashMap<Integer, Theams> getBuzyTeam() {
        HashMap<Integer, Theams>  busyTheam = new HashMap<>();
        log.info("in buzy method");
        try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement("select *" +
                    "from theam_group");
            ResultSet rs = ps.executeQuery();
            log.info("connection over");
            while (rs.next()) {
                Theams theams = theamById(rs.getInt("theam_id"));
                log.info("team take by id ={}"+theams.getId(), theams.getTheamName());
                busyTheam.put( theams.getId(),
                        theams
                );
                log.info("BUZY theam ADD = {}"+theams.getId(), theams.getTheamName());
            }
        } catch (SQLException e) {
            log.info("error", e.getMessage());
            e.printStackTrace();
        }
        return busyTheam;
    }

    private Integer findTheamIdByTheamName(Theams theams) {
               int theamID = 0;
                try {
            Connection connection = datasourse.getConnection();
            PreparedStatement ps = connection.prepareStatement("select id " +
                    "from theam  where theam_name = "+theams.getTheamName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
              theamID =   rs.getInt("id");
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
     return  theamID;
    }

}
