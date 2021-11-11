package repository.threadmodelrep.threadfunction.functioninpostgres;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryDatasourse;
import repository.RepositoryFactory;
import threadmodel.Mark;
import threadmodel.Theams;
import users.UserImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class MarkFunction {
    private static final RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();

    public static HashMap<UserImpl, HashMap<Theams, List<Mark>>> getstudentTheamMark(int studentId) {
        HashMap<UserImpl, HashMap<Theams, List<Mark>>> studentTheamMarkMap = new HashMap<>();
        HashMap <Theams, List <Mark>> theamsListHashMap = new HashMap<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("select * from mark where student_id = ?");
                ps.setInt(1, studentId);
                rs = ps.executeQuery();
                Set<Theams> theams = new HashSet<>();
                while (rs.next()) {
                    theams.add(TheamFunction.getallTheams().get(rs.getInt("theam_id")));
                }
                for (Theams theam:
                        theams) {
                    theamsListHashMap.put(theam, dogetMarkListbyTheam(theam,  studentId));
                }
            }
            catch (MySqlException e) {
                log.info("getStudentTheamMark exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(rs);
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("getStudentTheamMark connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
        studentTheamMarkMap.put(RepositoryFactory.getRepository().allStudent().get(studentId), theamsListHashMap);
        return  studentTheamMarkMap;
    }
    public static List<Mark> dogetMarkListbyTheam(Theams theam, int studentId) {
        List <Mark> marks = new ArrayList<>();
        log.info("In getMarkListMethd getTheam method = {}",theam.getTheamName()+studentId);
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("select * from mark where student_id = ? and theam_id = ? " );
                ps.setInt(1, studentId);
                ps.setInt(2, theam.getId());
                rs = ps.executeQuery();
                while (rs.next()) {
                    int tempMarkValue = rs.getInt("mark_value");
                    int markId = rs.getInt("id");
                    if (!rs.wasNull() && tempMarkValue!=0)
                    {
                        marks.add(new Mark(markId, tempMarkValue, null, null));
                        log.info("Marks ={}", theam.getTheamName() + " " + tempMarkValue);}
                }
            }
            catch (MySqlException e) {
                log.info("getStudentTheamMark exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(rs);
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.info("Marks ={}", e.getMessage());
        }
        return marks;
    }

    public static HashMap<Integer, Mark> dogetMarkIDListbyTheam(Theams theam, int studentId) {
        HashMap<Integer, Mark> marks = new HashMap<>();
        log.info("In getMarkListMethd getTheam method = {}",theam.getTheamName()+studentId);
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement("select * from mark where student_id = ? and theam_id = ? " );
                ps.setInt(1, studentId);
                ps.setInt(2, theam.getId());
                rs = ps.executeQuery();
                while (rs.next()) {
                    int markId = rs.getInt("id");
                    int tempMarkValue = rs.getInt("mark_value");
                    marks.put(markId ,new Mark(markId, tempMarkValue, null, null));
                    log.info("Marks ={}", theam.getTheamName() + " " + tempMarkValue);
                }
            }
            catch (MySqlException e) {
                log.info("dogetMarkIDListbyTheam exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(rs);
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("dogetMarkIDListbyTheam connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
        return marks;
    }
    public static void doaddMarkToStudent(int studentId, int theamID, int markValue) {
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement(
                        "select * from mark where student_id = ? and theam_id = ?");
                ps.setInt(1, studentId);
                ps.setInt(2, theamID);
                rs = ps.executeQuery();
                log.info("After ps before while ={}", studentId+ " "+theamID+" " + markValue);
                while (rs.next()) {
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
            }
            catch (MySqlException e) {
                log.info("addMarkToStudent exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(rs);
                PostgresSQLUtils.closeQuietly(ps);
            }

        } catch (SQLException e) {
            log.info("error = {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertMark(int studentId, int theamID, int markValue)  {
        log.info("in insert section = {} ", studentId+ " "+theamID+" " + markValue);
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(
                        "insert into mark (mark_value, student_id, theam_id) values (?, ?, ?)");
                ps.setInt(1, markValue);
                ps.setInt(2, studentId);
                ps.setInt(3, theamID);
                ps.executeUpdate();
            }
            catch (MySqlException e) {
                log.info("insertMark exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("error = {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private static void updateMark(int studentId, int theamID, int markValue) {
        log.info("in update section = {}", studentId+ " "+theamID+" " + markValue);
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(
                        "update mark set mark_value = ? where student_id = ? and theam_id = ?");

                ps.setInt(1, markValue);
                ps.setInt(2, studentId);
                ps.setInt(3, theamID);
                ps.executeUpdate();
            }
            catch (MySqlException e) {
                log.info("updateMark exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }

        } catch (SQLException e) {
            log.info("error ={}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void dodeleteMarksById(int[] tempMarksId, int theamId, int studentid) {
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = null;
            try {
                for (int j : tempMarksId) {
                    ps = connection.prepareStatement(
                            "delete from mark where id = ? ");
                    ps.setInt(1, j);
                    ps.executeQuery();
                }
            }
            catch (MySqlException e) {
                log.info("dodeleteMarksById exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("dodeleteMarksById connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void dochangeMark(HashMap<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = null;
            for (Map.Entry <Integer, Integer> entry: markIdMarkValue.entrySet()) {
                int tempId = entry.getKey();
                int tempMarkValue = entry.getValue();
                log.info("In changeRepository = {}", tempId + " " + tempMarkValue );
                try {
                    ps = connection.prepareStatement(
                            "update mark set mark_value = ? where id = ?");
                    ps.setInt(1, tempMarkValue);
                    ps.setInt(2, tempId);
                    ps.executeUpdate();
                }
                catch (MySqlException e) {
                    log.info("dochangeMark exception = {}", e.getMessage());
                }
                finally {
                    PostgresSQLUtils.closeQuietly(ps);
                }
            }
        } catch (SQLException e) {
            log.info("dochangeMark connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
    }

}
