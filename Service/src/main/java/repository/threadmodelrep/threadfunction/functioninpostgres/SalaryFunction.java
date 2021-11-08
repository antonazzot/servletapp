package repository.threadmodelrep.threadfunction.functioninpostgres;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryDatasourse;
import repository.RepositoryFactory;
import threadmodel.Salary;
import users.Trainer;
import users.UserImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Slf4j
public class SalaryFunction {
    private static final RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();

    public static HashMap<Trainer, List<Salary>> gettrainerSalary() {
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
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    ps = connection.prepareStatement(
                            "select * from salary where trainer_id = "+trainerId);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        int salaryValue = rs.getInt("salary_value");
                        if (!rs.wasNull())
                            trainer
                                    .addSalary(new Salary()
                                                    .withValue(salaryValue)
                                    );
                    }
                }
                catch (MySqlException e) {
                    log.info("getTrainerSalary exception = {}", e.getMessage());
                }
                finally {
                    PostgresSQLUtils.closeQuietly(rs);
                    PostgresSQLUtils.closeQuietly(ps);
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

    public static void doaddSalaryToTrainer(int trainerId, int salaryValue ) {
        try (Connection connection = datasourse.getConnection()){
            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(
                        "insert into salary (trainer_id, salary_value)" +
                                " values (?, ?)");
                ps.setInt(1, trainerId);
                ps.setInt(2, salaryValue);
                ps.executeUpdate();
                log.info("connection over");
            }
            catch (MySqlException e) {
                log.info("getTrainerSalary exception = {}", e.getMessage());
            }
            finally {
                PostgresSQLUtils.closeQuietly(ps);
            }

        } catch (SQLException e) {
            log.info("error ={}", e.getMessage());
            e.printStackTrace();
        }
    }
}