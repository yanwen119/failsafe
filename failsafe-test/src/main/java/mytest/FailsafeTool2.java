package mytest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.util.SocketUtils;

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

    /**
     * @param args
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void main(String[] args) throws Exception {
//        FailsafeTool2 failsafeTool2 = new FailsafeTool2();

//        circuitBreaker.halfOpen();
//        circuitBreaker.recordSuccess();
//        circuitBreaker.recordSuccess();
        
        CircuitBreaker breaker = new CircuitBreaker()
                .withFailureThreshold(1,3)
                .withSuccessThreshold(2)
                .withDelay(1, TimeUnit.SECONDS);
       
        System.out.println(breaker.allowsExecution());
        breaker.recordSuccess();
        breaker.recordFailure(new Exception());
        breaker.recordSuccess();
        System.out.println("1 - "+breaker.getState().toString()+" -"+breaker.allowsExecution() );
        Thread.sleep(2000l);
        breaker.allowsExecution();
        System.out.println("2 - "+breaker.getState().toString() );
        
        breaker.recordSuccess();
        breaker.recordFailure(new Exception());
        breaker.recordSuccess();
        
        System.out.println("3 - "+breaker.getState().toString()+" -"+breaker.allowsExecution() );
        
        
        System.out.println("3 - "+breaker.getState().toString()+" -"+breaker.allowsExecution() );
        System.out.println("3 - "+breaker.getState().toString()+" -"+breaker.allowsExecution() );
        
        
        // int errNum=100;
        // for (int i = 0; i < errNum; i++) {
        // failsafeTool2.saveException();
        // }

//        if (failsafeTool2.isBreak()) {
//            failsafeTool2.saveException();
//        }
//        if (failsafeTool2.isBreak()) {
//            failsafeTool2.saveSuccess();
//        }
//        if (failsafeTool2.isBreak()) {
//            failsafeTool2.saveSuccess();
//        }
//        if (failsafeTool2.isBreak()) {
//            failsafeTool2.saveException();
//        }
//        if (failsafeTool2.isBreak()) {
//            failsafeTool2.saveException();
//        }
//        
//        if (failsafeTool2.isBreak()) {
//            failsafeTool2.saveSuccess();
//        }
//        if (failsafeTool2.isBreak()) {
//            failsafeTool2.saveSuccess();
//        }
//        if (failsafeTool2.isBreak()) {
//            failsafeTool2.saveException();
//        }
//        
//        System.out.println("ok");
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
