package aspect;

import helperutils.myexceptionutils.ControllerException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class ControllerViewsException {

    @AfterThrowing(
            pointcut = "execution(* controller.viewscontrollers.*.*(..))",
            throwing = "ex"
    )
    public String loggingException(Exception ex) throws ControllerException {

        log.error("Controller exception ={}", ex.getMessage());
        return "exception";

    }
}
