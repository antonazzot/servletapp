package aspect;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
@Slf4j
@Aspect
public class LogingAspect {


    @Pointcut("execution(* controller.viewscontrollers.*.*(..))")
    public void controllers() {
        // pointcut
    }

    @Before("controllers()")
    public void before1(JoinPoint jp) {
        Arrays.stream(jp.getArgs()).forEach(arg -> log.info(arg.toString()));

        Arrays.stream(jp.getArgs())
                .filter(HttpServletRequest.class::isInstance)
                .findAny()
                .map(HttpServletRequest.class::cast)
                .ifPresent(req -> log.info("{} {}", req.getMethod(), ServletUriComponentsBuilder.fromRequest(req).toUriString()));
    }

    @Before("controllers()")
    public void before(JoinPoint jp) {
        log.info("login aspect ={}", jp.getSignature().getName());
    }

        @After("controllers()")
    public void after(JoinPoint jp) {
        log.info("Logging after controller's method: {}", jp.getSignature().getName());
    }

    @SneakyThrows
    @Around("controllers()")
    public Object around(ProceedingJoinPoint jp) {
        log.info("Logging around before method: {}", jp.getSignature().getName());
        log.info("----------------------------");
        log.info(jp.getKind());
        log.info(jp.toLongString());
        log.info(jp.toShortString());
        Arrays.stream(jp.getArgs()).forEach(System.out::println);
        log.info(jp.getStaticPart().toLongString());
        log.info(jp.getThis().toString());
        Object result = jp.proceed();
        log.info("----------------------------");
        log.info("Logging around after method: {}", jp.getSignature().getName());
        return result;
    }
}
