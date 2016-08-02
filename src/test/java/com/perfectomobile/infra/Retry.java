package com.perfectomobile.infra;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
    private static volatile int retryCount = 0;
    private static final int maxRetryCount = 4;

    public static synchronized void resetRetries(){
        retryCount = 0;
    }

    public boolean retry(ITestResult result) {
        if (result.isSuccess())
            return false;
        else{
            if (retryCount < maxRetryCount) {
                retryCount++;
                System.out.println("Retry #" + retryCount + " for test: " + result.getMethod().getMethodName() + ", on thread: " + Thread.currentThread().getName());
                return true;
            }
            return false;
        }
    }
}
