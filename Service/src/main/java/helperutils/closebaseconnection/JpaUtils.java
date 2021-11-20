package helperutils.closebaseconnection;

import helperutils.MyExceptionUtils.MyJpaException;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
@Slf4j
public class JpaUtils {

    private JpaUtils() {
    }

    public static void rollBackQuietly(EntityManager em, Exception e) throws MyJpaException {
        log.error(e.getMessage(), e);
        if (em != null) {
            em.getTransaction().rollback();
        }

            throw new MyJpaException("Null entityM");
    }

    public static void closeQuietly(EntityManager em) throws MyJpaException {
        if (em != null) {
            try {
                em.close();
            } catch (Exception ex) {
                log.error("Close Eror exception = {}", ex.getMessage());
            }
        }
        throw new MyJpaException("Null entityM");
    }
}
