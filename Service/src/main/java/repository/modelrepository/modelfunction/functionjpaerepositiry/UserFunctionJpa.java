package repository.modelrepository.modelfunction.functionjpaerepositiry;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import threadmodel.Group;
import users.Administrator;
import users.Student;
import users.Trainer;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
@Slf4j
public class UserFunctionJpa {
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();
    public static EntityManager em = sessionFactory.createEntityManager();

    public static UserImpl getUserById (Integer id) {
       return getAllUser().get(id);
    }

    public static HashMap<Integer, UserImpl> getAllUser() {
            HashMap <Integer, UserImpl> result = new HashMap<>();
            result.putAll(TrainerFunctionJpa.getallTrainer());
            result.putAll(StudentFunctionJpa.getAllStudent());
            result.putAll(AdminFunctionJpa.getAllAdmin());
            return result;
    }

    public static int doSaveUser(UserImpl user) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(user);
        transaction.commit();

        return user.getId();
    }

    public static Optional<UserImpl> doRemoveUser(Integer id, String entity) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        if (entity.equals("student")) {
            Student student = (Student) StudentFunctionJpa.getAllStudent().get(id);
            em.remove(student);
            transaction.commit();

            em.close();

        }
//        else  if
//        (entity.equals("trainer"))
//        {
//            Trainer trainer = (Trainer) TrainerFunctionJpa.getallTrainer().get(id);
//            em.remove(trainer);
//            transaction.commit();
//        }
//        else  if
//        (entity.equals("administrator"))
//        {
//            Administrator administrator = (Administrator) getUserById(id);
//            em.remove(administrator);
//            transaction.commit();
//        }
//       else
//                switch (entity) {
//
//                    case "group": {
//                        return  null;
//                    }
//
//                    case "theam": {
//                        return  null;
//                    }
//
//                }


        return Optional.empty();
    }

    public static UserImpl doUpdateUser(UserImpl user) {
        UserImpl userForChange = getUserById(user.getId());
        userForChange
                .withLogin(user.getLogin())
                .withPassword(user.getPassword())
                .withName(user.getName())
                .withAge(user.getAge())
                .withRole(user.getRole());
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(userForChange);
        transaction.commit();
        return userForChange;
    }
}
