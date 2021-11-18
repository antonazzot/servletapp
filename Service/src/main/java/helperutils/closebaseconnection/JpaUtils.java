package helperutils.closebaseconnection;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
@Slf4j
public class JpaUtils {

    private JpaUtils() {
    }

    public static void rollBackQuietly(EntityManager em, Exception e) {
        log.error(e.getMessage(), e);
        if (em != null) {
            em.getTransaction().rollback();
        }
    }

    public static void closeQuietly(EntityManager em) {
        if (em != null) {
            try {
                em.close();
            } catch (Exception ex) {
                log.error("Close Eror exception = {}", ex.getMessage());
            }
        }
    }
}
