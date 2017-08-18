package com.leaderliang.rxproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    public Disposable mDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //  创建上游
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onComplete();
//            }
//        });
//
//        //  创建下游
//        Observer<Integer> observer = new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                LogUtil.d(TAG,"onSubscribe");
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                LogUtil.d(TAG,"onNext "+value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LogUtil.d(TAG, "onError ");
//            }
//
//            @Override
//            public void onComplete() {
//                LogUtil.d(TAG, "onComplete");
//            }
//        };
//        observable.subscribe(observer);



        /*也可写成链式操作*/
        /**
         * ObservableEmitter  这个就是用来发出事件的, 通过调用emitter的onNext(T value)、onComplete()和onError(Throwable error)就可以分别发出next事件、complete事件和error事件
         *
         * Disposable 字面理解，一次性用品,用完即可丢弃的；RxJava中怎么去理解它呢, 对应于上面的水管的例子, 我们可以把它理解成两根管道之间的一个机关, 当调用它的dispose()方法时, 它就会将两根管道切断, 从而导致下游收不到事件
         */
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                LogUtil.d(TAG, "emit 1");
//                e.onNext(1);
//                LogUtil.d(TAG, "emit 2");
//                e.onNext(2);
//                LogUtil.d(TAG, "emit 3");
//                e.onNext(3);
//
////                e.onComplete();
//
////                Throwable error = new RuntimeException();
////                e.onError(error);
//            }
//        }).subscribe(new Observer<Integer>() {
//
//            @Override
//            public void onSubscribe(Disposable d) {
//                LogUtil.d(TAG,"onSubscribe");
//                mDisposable = d;
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                LogUtil.d(TAG,"onNext "+value);
//                if(value == 2){
//                    LogUtil.d(TAG, "dispose");
//                    mDisposable.dispose();//  在切断后，上游调用 onError 会崩溃
//                    LogUtil.d(TAG, "isDisposed "+mDisposable.isDisposed());
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LogUtil.d(TAG, "onError ");
//            }
//
//            @Override
//            public void onComplete() {
//                LogUtil.d(TAG, "onComplete");
//            }
//        });


//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                LogUtil.d(TAG, "emit 1");
//                emitter.onNext(1);
//                LogUtil.d(TAG, "emit 2");
//                emitter.onNext(2);
//                LogUtil.d(TAG, "emit 3");
//                emitter.onNext(3);
//                LogUtil.d(TAG, "emit complete");
//                emitter.onComplete();
//                LogUtil.d(TAG, "emit 4");
//                emitter.onNext(4);
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//
//            }
//        });

//         subscribe(new BlockingBaseObserver<Integer>() {
//            @Override
//            public void onNext(Integer value) {
//                LogUtil.d(TAG, "BlockingBaseObserver value "+value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//        })

//        subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Integer value) {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        })

        /* 切换线程  线程调度*/
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                LogUtil.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
//                LogUtil.d(TAG, "emit 1");
//                e.onNext(1);
//            }
//        });
//
//        Consumer<Integer> consumer = new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                LogUtil.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
//                LogUtil.d(TAG, "onNext: " + integer);
//            }
//        };
//
//        /*Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
//          Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
//          Schedulers.newThread() 代表一个常规的新线程
//          AndroidSchedulers.mainThread() 代表Android的主线程
//        */
//        observable.subscribeOn(Schedulers.newThread())// 指定的是上游发送事件的线程
//                .subscribeOn(Schedulers.io())// 多次指定上游的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
//                .observeOn(AndroidSchedulers.mainThread())// 指定的是下游接收事件的线程
//                .doOnNext(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        LogUtil.d(TAG, "After observeOn(mainThread), current thread is: " + Thread.currentThread().getName());
//                    }
//                })
//                .observeOn(Schedulers.io())// 多次指定下游的线程是可以的, 也就是说每调用一次observeOn() , 下游的线程就会切换一次
//                .doOnNext(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        LogUtil.d(TAG, "After observeOn(io), current thread is : " + Thread.currentThread().getName());
//                    }
//                })
//                .subscribe(consumer);



        /* 操作符 map */
        /* 操作符 map   integer 转换成 string 类型*/
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                LogUtil.d(TAG, "this is map----->");
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {

                return "this is result"+ integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtil.d(TAG, s);
            }
        });

        /*
        * 操作符 FlatMap 和 concatMap
        * concatMap 严格按照上游发送的顺序来执行
        * */
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                LogUtil.d(TAG, "this is flatMap----->");
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(2000, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtil.d(TAG, s);
            }
        });







    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*请求过程中 Activity 已经退出时，调用此方法切断下游主线程中的操作，避免崩溃
        * 那如果有多个Disposable 该怎么办呢, RxJava中已经内置了一个容器CompositeDisposable,
        * 每当我们得到一个Disposable时就调用CompositeDisposable.add()将它添加到容器中,
        * 在退出的时候, 调用CompositeDisposable.clear() 即可切断所有的水管
        */
        if(mDisposable != null) {
            mDisposable.dispose();
        }
//        new CompositeDisposable().clear();
    }
}
