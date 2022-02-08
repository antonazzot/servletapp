package repository.threadmodelrep.threadservices.postgresservices;

import helperutils.closebaseconnection.PostgresSQLUtils;
import helperutils.myexceptionutils.MySqlException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryServicesPostgres {

    private final RepositoryDatasourse datasourse;

    public Map<Trainer, List<Salary>> gettrainerSalary() {
        Map<Trainer, List<Salary>> result = new HashMap<>();
        List<Integer> trainersIDList = new ArrayList<>(RepositoryFactory.getRepository().allTrainer().keySet());
        try (Connection connection = datasourse.getConnection()) {
            for (Integer trainerId :
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
                            "select * from salary where trainer_id = " + trainerId);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        int salaryValue = rs.getInt("salary_value");
                        if (!rs.wasNull())
                            trainer
                                    .addSalary(new Salary()
                                            .withValue(salaryValue));
                    }
                } catch (MySqlException e) {
                    log.info("getTrainerSalary exception = {}", e.getMessage());
                } finally {
                    PostgresSQLUtils.closeQuietly(rs);
                    PostgresSQLUtils.closeQuietly(ps);
                }
                result.put(
                        trainer,
                        trainer.getSalarylist()
                );
            }
        } catch (SQLException e) {
            log.info("getTrainerSalary connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public void doaddSalaryToTrainer(int trainerId, int salaryValue) {
        try (Connection connection = datasourse.getConnection()) {
            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(
                        "insert into salary (trainer_id, salary_value)" +
                                " values (?, ?)");
                ps.setInt(1, trainerId);
                ps.setInt(2, salaryValue);
                ps.executeUpdate();
                log.info("connection over");
            } catch (MySqlException e) {
                log.info("getTrainerSalary exception = {}", e.getMessage());
            } finally {
                PostgresSQLUtils.closeQuietly(ps);
            }

        } catch (SQLException e) {
            log.info("getTrainerSalary connection exception = {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
