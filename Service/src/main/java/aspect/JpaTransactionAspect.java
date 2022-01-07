package aspect;

import helperutils.closebaseconnection.JpaUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Aspect
@Slf4j
@RequiredArgsConstructor
@Component
public class JpaTransactionAspect {
    private final SessionFactory sessionFactory;
    @SneakyThrows
    @Around("@annotation(JpaTransaction)")
    public Object transaction (ProceedingJoinPoint pjp) {
        Object result = null;

        EntityManager em = null;
        try {
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            result = pjp.proceed();

            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
        return result;
    }
}
