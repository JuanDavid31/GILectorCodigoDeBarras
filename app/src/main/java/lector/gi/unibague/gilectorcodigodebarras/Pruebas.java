package lector.gi.unibague.gilectorcodigodebarras;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Pruebas {

    public static void main(String[] args) throws InterruptedException {


        Completable c1 =Completable.fromAction(() -> {
            System.out.println("1 " + Thread.currentThread().getName());
        }).observeOn(Schedulers.io());
        Completable c2 = Completable.fromAction(() -> System.out.println("2 "+ Thread.currentThread().getName()));
        Completable c3 = Completable.fromAction(() -> System.out.println("3 "+ Thread.currentThread().getName()));
        c1.observeOn(Schedulers.io())
            .subscribe(() -> System.out.println("Termine " + Thread.currentThread().getName()));

        Thread.sleep(1000);
//
//        //Insercion, consulta, insercion, actualización
//        //Completable, Flowable, Completable, Completable
//
////        Completable c = Completable.fromAction(() -> System.out.println("Inserting data"));
////        Flowable<Integer> f = Flowable.just(1);
////        Completable c1 = Completable.fromSingle((x) -> System.out.println("Inserting more data with: " + x));
////        Completable c2 = Completable.fromAction(() -> System.out.println("Updating"));
////        f = f.doOnNext(x -> System.out.println(x));
////        f.subscribe();
//
//
//        Completable insert = Completable.fromAction(() -> System.out.println("Inserting data"));
//        Single<Integer> query = Single.just(1);
//        Completable update = Completable.fromAction(() -> System.out.println("Updating"));
//        Completable insertMore = query.flatMapCompletable(x ->
//                Completable.fromAction(() ->
//                        System.out.println("Inserting more data with: " + x)
//                ));
//
//        insert.andThen(insertMore).andThen(update).subscribe();
//
////        c.andThen(f).subscribe((x) -> System.out.println("I: " + x));
//
//
////        c.andThen(f).mergeWith(c1).mergeWith(c2).subscribe();
//
//
//
////        f.flatMap( codigo ->{
////            return Completable.fromAction(() -> System.out.println("Tengo el codigo: " + codigo));
////        });
//
//
//
////        f.mergeWith(c1).subscribe();
//
////        Observable<Integer> observable = Observable.just(1)
////                    .doOnSubscribe(disposable -> System.out.println("observable-subscribed: " + disposable));
////
////        Completable completable = Completable.create(emitter -> {
////            emitter.onComplete();
////        }).doOnSubscribe(x -> System.out.println("emitted:"+x));
////
////        completable.subscribe();
//
////        completable
////                .andThen(observable)
////                .subscribe(integer -> System.out.println("emitted:"+integer));
    }
}
