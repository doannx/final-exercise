package vn.elca.training.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectModule {
    private Logger log = Logger.getLogger(AspectModule.class);

    @Pointcut("execution(* vn.elca.training.service.IProjectService.*(..))")
    private void selectAll() {
    }

    @Around("selectAll()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        final Signature signature = pjp.getSignature();
        log.info("Starting to execute " + signature.getDeclaringTypeName() + "." + signature.getName());
        Object retVal = pjp.proceed();
        log.info("Finished to execute " + signature.getDeclaringTypeName() + "." + signature.getName());
        return retVal;
    }
}
