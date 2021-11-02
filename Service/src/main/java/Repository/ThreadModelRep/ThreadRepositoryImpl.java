package Repository.ThreadModelRep;

import Repository.RepositoryDatasourse;
import Repository.RepositoryFactory;
import ThreadModel.Group;
import ThreadModel.Mark;
import ThreadModel.Salary;
import ThreadModel.Theams;
import Users.Trainer;
import Users.UserImpl;
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
        try (Connection connection = datasourse.getConnection();) {
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
                                .withStudents(
                                        studentsFromGroup(groupId)
                                )
                                .withTheam(theamFromGroup(groupId))
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
        try (Connection connection = datasourse.getConnection()) {
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
    public HashMap<Trainer, List<Salary>> trainerSalary() {
        HashMap<Trainer, List<Salary>> result = new HashMap<>();
        ArrayList<Integer> trainersIDList = new ArrayList<>(RepositoryFactory.getRepository().allTrainer().keySet());
        try (Connection connection = datasourse.getConnection()) {
            for (Integer trainerId:
                    trainersIDList) {
                UserImpl user = RepositoryFactory.getRepository().getUserById(trainerId);
                Trainer trainer = (Trainer) new Trainer()
                        .withSalary(new ArrayList<>())
                        .withId(user.getId())
                        .withLogin(user.getLogin())
                        .withName(user.getName())
                        .withPassword(user.getPassword())
                        .withRole(user.getRole());

                PreparedStatement ps = connection.prepareStatement(
                        "select * from salary where trainer_id = "+trainerId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                        int salaryValue = rs.getInt("salary_value");
                         if (!rs.wasNull())
                           trainer
                            .addSalary(
                                    new Salary()
                                            .withValue(salaryValue)
                            );
                }
                result.put(
                        trainer,
                        trainer.getSalarylist()

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public HashMap<UserImpl, HashMap<Theams, List<Mark>>> studentTheamMark( int studentId) {
        HashMap<UserImpl, HashMap<Theams, List<Mark>>> studentTheamMarkMap = new HashMap<>();
        HashMap <Theams, List <Mark>> theamsListHashMap = new HashMap<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from mark where student_id = ?");
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            Set <Theams> theams = new HashSet<>();
            while (rs.next()) {
                        theams.add(allTheams().get(rs.getInt("theam_id")));
                       Theams theam = allTheams().get(rs.getInt("theam_id"));
            }
            for (Theams theam:
                 theams) {
                theamsListHashMap.put(theam, getMarkListbyTheam(theam,  studentId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        studentTheamMarkMap.put(RepositoryFactory.getRepository().allStudent().get(studentId), theamsListHashMap);
        return  studentTheamMarkMap;
    }
    @Override
    public List<Mark> getMarkListbyTheam(Theams theam, int studentId) {
        List <Mark> marks = new ArrayList<>();
        log.info("In getMarkListMethd getTheam method = {}",theam.getTheamName()+studentId);
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from mark where student_id = ? and theam_id = ? " );
            ps.setInt(1, studentId);
            ps.setInt(2, theam.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int tempMarkValue = rs.getInt("mark_value");
                int markId = rs.getInt("id");
                if (!rs.wasNull() && tempMarkValue!=0)
                {
               marks.add(new Mark(markId, tempMarkValue));
               log.info("Marks ={}", theam.getTheamName() + " " + tempMarkValue);}
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.info("Marks ={}", e.getMessage());
        }
        return marks;
    }
    @Override
    public HashMap<Integer, Mark> getMarkIDListbyTheam(Theams theam, int studentId) {
        HashMap<Integer, Mark> marks = new HashMap<>();
        log.info("In getMarkListMethd getTheam method = {}",theam.getTheamName()+studentId);
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from mark where student_id = ? and theam_id = ? " );
            ps.setInt(1, studentId);
            ps.setInt(2, theam.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int markId = rs.getInt("id");
                int tempMarkValue = rs.getInt("mark_value");
                marks.put(markId ,new Mark(markId, tempMarkValue));
                log.info("Marks ={}", theam.getTheamName() + " " + tempMarkValue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marks;
    }


    @Override
    public Theams theamById(Integer id) {
        Theams theams = null;
        try (Connection connection = datasourse.getConnection()) {
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
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "select * from theam_group where group_id = " + groupId
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

    @Override
    public HashMap<Integer, UserImpl> studentsFromGroup(int groupId) {
        HashMap<Integer, UserImpl> result = new HashMap<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "select * from student_group where group_id = " + groupId
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserImpl student = RepositoryFactory.getRepository()
                        .getUserById(rs.getInt("student_id"));
                result.put(student.getId(), student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }
    @Override
    public void addTheam(String theam) {
        if (!allTheams().values().stream().map(Theams::getTheamName)
                .collect(Collectors.toList()).contains(theam))
        {
            try (Connection connection = datasourse.getConnection()) {
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
    @Override
    public void addGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {

        try (Connection connection = datasourse.getConnection()) {
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
            insertIntoMarkTable(studentList,theamsIdList);
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    private void insertIntoMarkTable(List<UserImpl> studentList, List<Integer> theamsIdList) {
        try (Connection connection = datasourse.getConnection()) {
            for (Integer i:
                    theamsIdList) {
                for (UserImpl student :
                studentList) {
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO mark (student_id, theam_id) " +
                                    "Values (?,?)"
                    );
                    ps.setInt(1, student.getId());
                    ps.setInt(2, i);
                    ps.executeUpdate();
                    ps.close();
                }
               }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertIntoTheam_Group(int groupId, List<Integer> theamsIdList) {
        try (Connection connection = datasourse.getConnection()) {
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
        try (Connection connection = datasourse.getConnection()) {
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
        try (Connection connection = datasourse.getConnection()){
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
    @Override
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

    }

    private HashMap<Integer, Theams> getBuzyTeam() {
        HashMap<Integer, Theams>  busyTheam = new HashMap<>();
        log.info("in buzy method");
        try (Connection connection = datasourse.getConnection()){
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
            log.info("error ={}", e.getMessage());
            e.printStackTrace();
        }
        return busyTheam;
    }
    @Override
    public void addSalaryToTrainer(int trainerId, int salaryValue ) {
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = connection.prepareStatement(
                    "insert into salary (trainer_id, salary_value)" +
                            " values (?, ?)");
            ps.setInt(1, trainerId);
            ps.setInt(2, salaryValue);
            ps.executeUpdate();
            log.info("connection over");

        } catch (SQLException e) {
            log.info("error ={}", e.getMessage());
            e.printStackTrace();
        }

    }


    @Override
    public void addMarkToStudent(int studentId, int theamID, int markValue) {
           try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = connection.prepareStatement(
                    "select * from mark where student_id = ? and theam_id = ?");
            ps.setInt(1, studentId);
            ps.setInt(2, theamID);
            ResultSet rs = ps.executeQuery();
               log.info("After ps before whule ={}", studentId+ " "+theamID+" " + markValue);
            if (rs.next()) {
                  int tempMarkValue = rs.getInt("mark_value");
                if (rs.wasNull() || tempMarkValue==0 )
                {
                    log.info("in while 'if' section = {}", studentId+ " "+theamID+" " + markValue);
                    updateMark(studentId, theamID, markValue);
                }

                else {
                    log.info("in while 'else' section = {}", studentId+ " "+theamID+" " + markValue);
                    insertMark( studentId,  theamID,  markValue);
                }

            }
            log.info("connection over");

        } catch (SQLException e) {
            log.info("error = {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private void insertMark(int studentId, int theamID, int markValue) {
        log.info("in insert section = {} ", studentId+ " "+theamID+" " + markValue);
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = connection.prepareStatement(
                    "insert into mark (mark_value, student_id, theam_id) values (?, ?, ?)");

            ps.setInt(1, markValue);
            ps.setInt(2, studentId);
            ps.setInt(3, theamID);
            ps.executeQuery();

        } catch (SQLException e) {
            log.info("error = {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateMark(int studentId, int theamID, int markValue) {
        log.info("in update section = {}", studentId+ " "+theamID+" " + markValue);
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = connection.prepareStatement(
                    "update mark set mark_value = ? where student_id = ? and theam_id = ?");

            ps.setInt(1, markValue);
            ps.setInt(2, studentId);
            ps.setInt(3, theamID);
            ps.executeQuery();

        } catch (SQLException e) {
            log.info("error ={}", e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void deleteMarksById(int[] tempMarksId, int theamId, int studentid) {
        try (Connection connection = datasourse.getConnection()){
            for (int j : tempMarksId) {
                PreparedStatement ps = connection.prepareStatement(
                        "delete from mark where id = ? ");
                ps.setInt(1, j);
                ps.executeQuery();
            }
        } catch (SQLException e) {
            log.info("error ={}", e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void changeMark(HashMap<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
        try (Connection connection = datasourse.getConnection()){
           for (Map.Entry <Integer, Integer> entry: markIdMarkValue.entrySet()) {
               int tempId = entry.getKey();
               int tempMarkValue = entry.getValue();
               log.info("In changeRepository = {}", tempId + " " + tempMarkValue );
               PreparedStatement ps = connection.prepareStatement(
                       "update mark set mark_value = ? where id = ?");
               ps.setInt(1, tempMarkValue);
               ps.setInt(2, tempId);
               ps.executeUpdate();
               log.info("after execute" );
           }
        } catch (SQLException e) {
            log.info("error ={}", e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void updateGroup(int groupId, String act, int[] entytiIdforact) {
        log.info("In repository updateGroup = {}", groupId + " " + "  " + act + " " + Arrays.toString(entytiIdforact));
        try (Connection connection = datasourse.getConnection()) {
            switch (act) {
                case "studentdelete":
                    for (int item : entytiIdforact) {
                        PreparedStatement ps = connection.prepareStatement
                                ("delete from student_group where student_id = ? and group_id =?");
                        ps.setInt(1, item);
                        ps.setInt(2, groupId);
                        ps.executeUpdate();
                        ps.close();
                    }
                    break;
                case "studentadd":
                    for (int value : entytiIdforact) {
                        PreparedStatement ps = connection.prepareStatement
                                ("insert into student_group (student_id, group_id)  values (?, ?)");
                        ps.setInt(1, value);
                        ps.setInt(2, groupId);
                        ps.executeUpdate();
                        ps.close();
                    }
                    break;
                case "theamdelete":
                    for (int k : entytiIdforact) {
                        PreparedStatement ps = connection.prepareStatement
                                ("delete from theam_group where theam_id = ? and group_id =?");
                        ps.setInt(1, k);
                        ps.setInt(2, groupId);
                        ps.executeUpdate();
                        ps.close();
                    }
                    break;
                case "theamadd":
                    for (int j : entytiIdforact) {
                        PreparedStatement ps = connection.prepareStatement
                                ("insert into theam_group (theam_id, group_id) values (?, ?) ");
                        ps.setInt(1, j);
                        ps.setInt(2, groupId);
                        ps.executeUpdate();
                        ps.close();
                    }
                    break;
                case "trainer":
                    PreparedStatement ps = connection.prepareStatement
                            ("update \"group\" set trainer_id = ? where id = ? ");
                    ps.setInt(1, entytiIdforact[0]);
                    ps.setInt(2, groupId);
                    ps.executeUpdate();
                    ps.close();
                    break;
            }
        }
        catch (SQLException e) {
            log.info("EROR in SQL = {} ", e.getMessage());
                    }

    }
    @Override
    public void updateTheam(int theamId, String theamName) {
        try (Connection connection = datasourse.getConnection()){

                log.info("In updateTheamRepository = {}", theamId + " " + theamName );
                PreparedStatement ps = connection.prepareStatement(
                        "update theam set theam_name = ? where id = ?");
                ps.setString(1, theamName);
                ps.setInt(2, theamId);
                ps.executeUpdate();

        } catch (SQLException e) {
            log.info("error ={}", e.getMessage());
            e.printStackTrace();
        }
    }
}

