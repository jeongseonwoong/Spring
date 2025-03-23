package personal.practice.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // AOP 적용을 위한 어노테이션
@Component
public class TimeTraceAOP {

    @Around("execution(* personal.practice..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        System.out.println("startTime = " + startTime);
        try{
            return joinPoint.proceed();
        } finally {
            long finishTime = System.currentTimeMillis();
            long timeMs = finishTime - startTime;
            System.out.println("End = " + joinPoint.toString() + " " + timeMs + " ms");
        }

    }
}
