package rxjava;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class RxjavaWindowTest {
    public static void main(String[] args) throws InterruptedException {
        Observable<Integer> source = Observable.interval(1000, TimeUnit.MILLISECONDS).map(i -> {
            int a = RandomUtils.nextInt(2);
            return a;
        });
//        source.window(10, TimeUnit.SECONDS).subscribe(window -> {
//            int[] metrics = new int[2];
//            window.subscribe(i -> metrics[(int) i]++, j -> {
//            }, () -> System.out.println("窗口Metrics:" + JSON.toJSONString(metrics)));
//
//        });

        // 跳过2个数据，取最新的20个数据，初始的时候会见20个，之后每次新建2个
        // 从而达到滑动的目的
        // source.window(20, 2).subscribe(window -> {
        // int[] metrics = new int[2];
        // window.subscribe(i -> metrics[(int) i]++, j -> {
        // }, () -> System.out.println("窗口Metrics:" +
        // JSON.toJSONString(metrics)));
        //
        // });

        // source.window(10, TimeUnit.SECONDS).flatMap(x -> {return
        // x;}).window(20, 2).subscribe(window -> {
        // int[] metrics = new int[2];
        // window.subscribe(i -> metrics[(int) i]++, j -> {
        // }, () -> System.out.println("窗口Metrics:" +
        // JSON.toJSONString(metrics)));
        //
        // });
        
        BehaviorSubject<Integer> counterSubject = BehaviorSubject.create();
//        Observable.create(new Observable.OnSubscribe<Integer>() {
//            @Override
//            public void call(Subscriber<? super Integer> t) {
//              t.onNext(RandomUtils.nextInt(2));
//                t.onCompleted();
//            }
//        }).subscribeOn(Schedulers.io()).subscribe(counterSubject);
        
        
        source.subscribe(counterSubject);
        System.out.println(counterSubject.getValue());
        
        counterSubject.subscribe(new Action1<Integer>() {

            @Override
            public void call(Integer t) {
                // TODO Auto-generated method stub
                System.out.println(t);
            }
        });
        
        
        TimeUnit.SECONDS.sleep(5000);

    }
}
