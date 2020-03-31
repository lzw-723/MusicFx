package io.github.lzw;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Application;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }

    public static void name() {
        Observable.create(new ObservableOnSubscribe<File>() {

			@Override
			public void subscribe(ObservableEmitter<File> e) throws Exception {
				// e.onNext(value);
			}
        })
        .subscribeOn(Schedulers.newThread())
        .subscribe(new Observer<File>() {

			@Override
			public void onSubscribe(Disposable d) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNext(File value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				
			}
        });
    }
}