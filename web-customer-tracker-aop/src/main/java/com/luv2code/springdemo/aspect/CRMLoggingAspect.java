package com.luv2code.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	Logger logger = Logger.getLogger(getClass().getName());

	// setup pointcut declarations
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {
	}

	// do the same for service and dao
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {
	}

	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDaoPackage() {
	}

	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {
	}

	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {

		// display method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		logger.info("=====>> in @Before: calling method: " + theMethod);

		// display the arguments to the method

		// get the arguments
		Object[] args = theJoinPoint.getArgs();

		// loop thru and display args
		for (Object tempArg : args) {
			logger.info("=====>> argument: " + tempArg);
		}

	}

	@AfterReturning(pointcut = "forAppFlow()", returning = "result")
	public void after(JoinPoint joinPoint, Object result) {
		logger.info("========>>>> in @AfterReturning: from methode: "
				+ joinPoint.getSignature().toShortString());

		logger.info("========>>>> result : " + result);
	}
}
