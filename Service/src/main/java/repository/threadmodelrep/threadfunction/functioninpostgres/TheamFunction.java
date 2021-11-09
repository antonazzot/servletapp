package repository.threadmodelrep.threadfunction.functioninpostgres;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryDatasourse;
import threadmodel.Theams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class TheamFunction {
    private static final RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();

    public static HashMap<Integer, Theams> getallTheams() {
        HashMap<Integer, Theams> result = new HashMap<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
               ps = connection.prepareStatement("select * from theam");
               rs = ps.executeQuery();
                while (rs.next()) {
                    int theamId = rs.getInt("id");
                    result.put(theamId,
                            new Theams()
                                    .withId(theamId)
                                    .withValue(rs.getString("theam_name"))
                    );
                }
            }
            catch (MySqlException e) {
                log.info("getallTheam exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(rs);
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("getallTheam connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static Theams gettheamById(Integer id) {
        Theams theams = null;
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                 ps = connection.prepareStatement("select * from theam where id = " + id);
                 rs = ps.executeQuery();
                while (rs.next()){
                    theams = new Theams()
                            .withId(rs.getInt("id"))
                            .withValue(rs.getString("theam_name"));
                }
            }
            catch (MySqlException e) {
                log.info("getTheamByID exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(rs);
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("getTheamByID connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
        return theams;
    }
    public static Set<Theams> gettheamFromGroup(Integer groupId)  {
        Set <Theams> result = new HashSet<>();
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement(
                        "select * from theam_group where group_id = " + groupId
                );
                rs = ps.executeQuery();
                while (rs.next()) {
                    result.add(gettheamById(rs.getInt("theam_id")));
                }
            }
            catch (MySqlException e) {
                log.info("getTheamByID exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(rs);
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void doaddTheam(String theam) {
        if (!getallTheams().values().stream().map(Theams::getTheamName)
                .collect(Collectors.toList()).contains(theam) &&
                getallTheams().values().stream().map(Theams::getTheamName)
                        .noneMatch(theamName -> theamName.equals(theam)))
        {
            try (Connection connection = datasourse.getConnection()) {
                PreparedStatement ps = null;
                try {
                    ps = connection.prepareStatement(
                            "INSERT INTO theam (theam_name) " +
                                    "Values (?)"
                    );
                    ps.setString(1, theam);
                    ps.executeUpdate();
                }
                catch (MySqlException e) {
                    log.info("doaddTheam exception = {}", e.getMessage());
                }
                finally {
                    PostgresSQLUtils.closeQuietly(ps);
                }
            } catch (SQLException e) {
                log.info("doaddTheam connection exception = {}", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static HashMap<Integer, Theams> getfreeTheams() {
        HashMap <Integer,Theams> busyTheam = getBuzyTeam();
        HashMap <Integer,Theams> freeTh = new HashMap<>(getallTheams());
        log.info("free Start {}" , busyTheam.values());
        if (GroupFunction.getAllGroup().isEmpty())
        {
            return getallTheams();}
        else {
            log.info("in for {}" , busyTheam.values());
            for (Theams allTh:
                    getallTheams().values()) {
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
    private static HashMap<Integer, Theams> getBuzyTeam() {
        HashMap<Integer, Theams>  busyTheam = new HashMap<>();
        log.info("in buzy method");
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = null;
            ResultSet rs = null;
             try {
                 ps = connection.prepareStatement("select *" +
                         "from theam_group");
                 rs = ps.executeQuery();
                 log.info("connection over");
                 while (rs.next()) {
                     Theams theams = gettheamById(rs.getInt("theam_id"));
                     log.info("team take by id ={}"+theams.getId(), theams.getTheamName());
                     busyTheam.put( theams.getId(),theams);
                     log.info("BUZY theam ADD = {}"+theams.getId(), theams.getTheamName());
                 }
             }
             catch (MySqlException e) {
                 log.info("getTheamByID exception = {}", e.getMessage());
             }
             finally {
                 PostgresSQLUtils.closeQuietly(rs);
                 PostgresSQLUtils.closeQuietly(ps);
             }
        } catch (SQLException e) {
            log.info("getBuzyTeam connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
        return busyTheam;
    }

    public static void doupdateTheam(int theamId, String theamName) {
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = null;
            try {
                log.info("In updateTheamRepository = {}", theamId + " " + theamName );
                ps = connection.prepareStatement(
                        "update theam set theam_name = ? where id = ?");
                ps.setString(1, theamName);
                ps.setInt(2, theamId);
                ps.executeUpdate();
            }
            catch (MySqlException e) {
                log.info("doupdateTheam exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }
        } catch (SQLException e) {
            log.info("doupdateTheam connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
