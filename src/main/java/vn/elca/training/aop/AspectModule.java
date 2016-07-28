package vn.elca.training.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectModule {
    /**
     * Following is the definition for a pointcut to select all the methods available. So advice will be called for all
     * the methods.
     */
    @Pointcut("execution(* vn.elca.training.service.IProjectService.*(..))")
    private void selectAll() {
    }

    @Around("selectAll()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        final Signature signature = pjp.getSignature();
        System.out.println("Starting to execute " + signature.getDeclaringTypeName() + "." + signature.getName());
        Object retVal = pjp.proceed();
        System.out.println("Finished to execute " + signature.getDeclaringTypeName() + "." + signature.getName());
        return retVal;
    }
}
