package helperutils.closebaseconnection;

import helperutils.MyExceptionUtils.MyJpaException;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
@Slf4j
public class JpaUtils {

    private JpaUtils() {
    }

    public static void rollBackQuietly(EntityManager em, Exception e) {
        log.error(e.getMessage(), e);
      try {
          if (em != null) {
              em.getTransaction().rollback();
          }
          throw new MyJpaException("Null entityM");
      }
     catch (Exception ex) {
        log.error("Close Eror exception = {}", ex.getMessage());
    }
    }

    public static void closeQuietly(EntityManager em) {

            try {if (em != null) {
                em.close();}
                throw new MyJpaException("Null entityM");
            } catch (Exception ex) {
                log.error("Close Eror exception = {}", ex.getMessage());
            }

    }
}
