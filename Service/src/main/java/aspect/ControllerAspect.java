package aspect;

import helperutils.myexceptionutils.ControllerException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@Aspect
public class ControllerAspect {
//    @SneakyThrows
//    @Around("execution(* controller.viewscontrollers.*.*(..))")
//    public String exceptionPage (ProceedingJoinPoint jp) {
//
//        try {
//            jp.proceed();
//        }
//        catch (Exception ex) {
//            log.error(ex.getMessage());
//            return "exception";
//        }
//        return "";
//    }
}
