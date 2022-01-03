package aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class LogingAspect {
    @Before("execution(* controller.*.*(..))")
public void before(JoinPoint jp) {
    log.info("login aspect ={}", jp.getSignature().getName());
}
}
