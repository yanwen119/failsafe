package mytest;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.jodah.failsafe.AsyncExecution;
import net.jodah.failsafe.AsyncFailsafe;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.FailsafeFuture;
import net.jodah.failsafe.function.AsyncCallable;
import net.jodah.failsafe.function.AsyncRunnable;
import net.jodah.failsafe.function.CheckedRunnable;
import net.jodah.failsafe.function.Predicate;

public class FailsafeTool {
    // private static ScheduledExecutorService scheduler =
    // Executors.newScheduledThreadPool(5);
    private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        @SuppressWarnings("unchecked")
        CircuitBreaker circuitBreaker = new CircuitBreaker().withFailureThreshold(2)// 连续失败
                // .withFailureThreshold(1, 2)//失败频率,这两个不能同时配置
                .withSuccessThreshold(2)//成功次数
                // .withSuccessThreshold(1, 2)//成功率
                .withTimeout(2000, TimeUnit.MILLISECONDS).withDelay(10, TimeUnit.SECONDS);//.failIf((result,failure)->{ return false;});//true代表出错
        
        int num = 1;
        //circuitBreaker.recordFailure(new Throwable("er"));
        CheckedRunnable runnable1 = new CheckedRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < num; i++) {
                    System.out.println("开始执行任务" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        
        Callable<String> callable1=new Callable<String>() {
            
            @Override
            public String call() throws Exception {
                return "ok";
            }
        };
        
        AsyncCallable<String> callable = new AsyncCallable<String>() {
            @Override
            public String call(AsyncExecution execution) throws Exception {
                //Thread.sleep(1000);
                // execution.complete("开始执行任务");
                 return "bac";
                //throw new RuntimeException("测试异常");
            }
        };
        
        AsyncRunnable runnable=new AsyncRunnable() {
            
            @Override
            public void run(AsyncExecution execution) throws Exception {
                System.out.println("开始任务");
            }
        };
        
        // SyncFailsafe<Object> failsafe = Failsafe.with(circuitBreaker);
        AsyncFailsafe<Object> failsafe = Failsafe.with(circuitBreaker).with(scheduler);
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("=======" + i + "=======");
                //同步run&get
                failsafe.run(runnable1).get();
                //System.out.println(failsafe.get(callable1).get());
                
                //异步run&get
                //failsafe.runAsync(runnable).get();
//                try {
//                    FailsafeFuture<String> future = failsafe.getAsync(callable);
//                    System.out.println(future.get(1, TimeUnit.SECONDS));
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }

            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for (int i = 0; i < 10; i++) {
                System.out.println("=======" + i + "=======");
                //failsafe.run(runnable);
                FailsafeFuture<String> future = failsafe.getAsync(callable);
                System.out.println(future.get());
            }
        }

        System.out.println("over");
        System.out.println("ok");
    }

}
