package mytest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.jodah.failsafe.CircuitBreaker;

public class FailsafeTool2 {
    private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    static CircuitBreaker circuitBreaker = new CircuitBreaker()// .withFailureThreshold(2)//
                                                               // 连续失败
            .withFailureThreshold(2, 3)// 失败频率,这两个不能同时配置
            .withSuccessThreshold(2)
            // .withSuccessThreshold(1, 2)
            .withTimeout(500, TimeUnit.MILLISECONDS).withDelay(30, TimeUnit.SECONDS).failIf((record)->{
                return (boolean)record;}
            );

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FailsafeTool2 failsafeTool2 = new FailsafeTool2();

        circuitBreaker.halfOpen();
        circuitBreaker.recordSuccess();
        circuitBreaker.recordSuccess();
        
        // int errNum=100;
        // for (int i = 0; i < errNum; i++) {
        // failsafeTool2.saveException();
        // }

        if (failsafeTool2.isBreak()) {
            failsafeTool2.saveException();
        }
        if (failsafeTool2.isBreak()) {
            failsafeTool2.saveSuccess();
        }
        if (failsafeTool2.isBreak()) {
            failsafeTool2.saveSuccess();
        }
        if (failsafeTool2.isBreak()) {
            failsafeTool2.saveException();
        }
        if (failsafeTool2.isBreak()) {
            failsafeTool2.saveException();
        }
        
        if (failsafeTool2.isBreak()) {
            failsafeTool2.saveSuccess();
        }
        if (failsafeTool2.isBreak()) {
            failsafeTool2.saveSuccess();
        }
        if (failsafeTool2.isBreak()) {
            failsafeTool2.saveException();
        }
        
        System.out.println("ok");
    }

    public boolean isBreak() {
        return circuitBreaker.allowsExecution();
    }

    public void saveException() {
        //这两种方式都会调用failif中的逻辑
        circuitBreaker.recordResult("false");
        //circuitBreaker.recordFailure(new Exception("err"));
    }

    public void saveSuccess() {
        circuitBreaker.recordSuccess();
    }
}
