package mytest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.jodah.failsafe.AsyncFailsafe;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.function.CheckedRunnable;

public class FailsafeTest {
    // private static ScheduledExecutorService scheduler =
    // Executors.newScheduledThreadPool(5);
    private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CircuitBreaker circuitBreaker = new CircuitBreaker().withFailureThreshold(5)// 连续失败
                // .withFailureThreshold(1, 2)//失败频率,与失败数不能同时配置
                .withSuccessThreshold(2)// 成功次数
                //.withSuccessThreshold(1, 20)//成功率,与成功数不能同时配置
                .withTimeout(100, TimeUnit.MILLISECONDS).withDelay(1, TimeUnit.SECONDS);

        CheckedRunnable runnable = new CheckedRunnable() {
            @Override
            public void run() throws InterruptedException {
                Thread.sleep(200);
                System.out.println("doSth");
            }
        };

        AsyncFailsafe<Object> failsafe = Failsafe.with(circuitBreaker).with(scheduler);
        for (int i = 0; i < 100; i++) {
            try {
                System.out.println("=======" + i + "=======");
                failsafe.run(runnable).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("over");
    }
}
