package rxjava;


import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class HelloWorld {
    public static void main(String[] args) {
        //hello("张三","李四");
        subjectTest();
    }
    
    private static void hello(String... names){
//        Observable.from(names).subscribe(new Action1<String>() {
//            @Override
//            public void call(String t) {
//                System.out.println("hello:"+t);
//                
//            }
//        });
        
        Observable.from(names).subscribe(x-> System.out.println("hello:"+x));
//        Observable.just(names).subscribe(new Action1<String[]>() {
//
//            @Override
//            public void call(String[] t) {
//                
//            }
//            
//        });
    }
    
    private static Subject<String,String> subject=PublishSubject.create();
    
    private static void subjectTest(){
        subject.doOnNext(new Action1<String>() {
            @Override
            public void call(String t) {
                System.out.println(t);
            }
        });
        subject.subscribe(new Action1<String>() {

            @Override
            public void call(String t) {
                // TODO Auto-generated method stub
                System.out.println(t);
            }
            
        });
        subject.onNext("中午");
    }
  
    
}
