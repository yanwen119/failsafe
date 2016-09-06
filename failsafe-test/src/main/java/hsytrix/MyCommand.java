package hsytrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class MyCommand extends HystrixCommand<String> {

    /**
     * // 熔断器在整个统计时间内是否开启的阀值，默认20秒。也就是10秒钟内至少请求20次，熔断器才发挥起作用  
        private final HystrixProperty<Integer> circuitBreakerRequestVolumeThreshold;   
        //熔断器默认工作时间,默认:5秒.熔断器中断请求5秒后会进入半打开状态,放部分流量过去重试  
        private final HystrixProperty<Integer> circuitBreakerSleepWindowInMilliseconds;   
        //是否启用熔断器,默认true. 启动  
        private final HystrixProperty<Boolean> circuitBreakerEnabled;   
        //默认:50%。当出错率超过50%后熔断器启动.  
        private final HystrixProperty<Integer> circuitBreakerErrorThresholdPercentage;  
        //是否强制开启熔断器阻断所有请求,默认:false,不开启  
        private final HystrixProperty<Boolean> circuitBreakerForceOpen;   
        //是否允许熔断器忽略错误,默认false, 不开启  
        private final HystrixProperty<Boolean> circuitBreakerForceClosed;  
        
        //使用命令调用隔离方式,默认:采用线程隔离,ExecutionIsolationStrategy.THREAD  
        private final HystrixProperty<ExecutionIsolationStrategy> executionIsolationStrategy;   
        //使用线程隔离时，调用超时时间，默认:1秒  
        private final HystrixProperty<Integer> executionIsolationThreadTimeoutInMilliseconds;   
        //线程池的key,用于决定命令在哪个线程池执行  
        private final HystrixProperty<String> executionIsolationThreadPoolKeyOverride;   
        //使用信号量隔离时，命令调用最大的并发数,默认:10  
        private final HystrixProperty<Integer> executionIsolationSemaphoreMaxConcurrentRequests;  
        //使用信号量隔离时，命令fallback(降级)调用最大的并发数,默认:10  
        private final HystrixProperty<Integer> fallbackIsolationSemaphoreMaxConcurrentRequests;   
        //是否开启fallback降级策略 默认:true   
        private final HystrixProperty<Boolean> fallbackEnabled;   
        // 使用线程隔离时，是否对命令执行超时的线程调用中断（Thread.interrupt()）操作.默认:true  
        private final HystrixProperty<Boolean> executionIsolationThreadInterruptOnTimeout;   
        // 统计滚动的时间窗口,默认:5000毫秒circuitBreakerSleepWindowInMilliseconds  
        private final HystrixProperty<Integer> metricsRollingStatisticalWindowInMilliseconds;  
        // 统计窗口的Buckets的数量,默认:10个,每秒一个Buckets统计  
        private final HystrixProperty<Integer> metricsRollingStatisticalWindowBuckets; // number of buckets in the statisticalWindow  
        //是否开启监控统计功能,默认:true  
        private final HystrixProperty<Boolean> metricsRollingPercentileEnabled;   
        // 是否开启请求日志,默认:true  
        private final HystrixProperty<Boolean> requestLogEnabled;   
        //是否开启请求缓存,默认:true  
        private final HystrixProperty<Boolean> requestCacheEnabled; // Whether request caching is enabled.  
     * @param commandGroup
     */
    public MyCommand(String commandGroup,String commandKey,String threadPoolKey) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(commandGroup))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(threadPoolKey))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.defaultSetter())
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)// 采用信号量的形式做控制
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(1000)// 设置信号量最大并发数
                        .withCircuitBreakerSleepWindowInMilliseconds(30000)// 设置熔断时间
                        .withCircuitBreakerErrorThresholdPercentage(50)// 设置熔断触发百分比
                        .withMetricsHealthSnapshotIntervalInMilliseconds(501)// 采样间隔，影响熔断的准确行
                        .withExecutionTimeoutEnabled(false)// 是否使用超时时间限制
                        // .withExecutionTimeoutInMilliseconds(100)//设置单次执行超时时间
                        .withFallbackEnabled(false)// 是否使用fallback
                        // .withRequestCacheEnabled(false)//是否使用请求缓存

                        .withMetricsRollingPercentileWindowInMilliseconds(100)
                        .withMetricsRollingPercentileWindowBuckets(10)
        // .withExecutionIsolationThreadInterruptOnTimeout(false)
        // .withMetricsRollingPercentileWindowBuckets(6)
        // .withCircuitBreakerRequestVolumeThreshold(10)
        )); 
    }

    @Override
    protected String run() throws Exception {
        System.out.println("start 同步测试");
        Thread.sleep(150);
        // TODO Auto-generated method stub
        System.out.println("end 同步测试");
        return "同步测试";
        //throw new Exception("error");
    }
    
    @Override
    protected String getFallback() {
        return "执行降级处理";
    }
    
    
}