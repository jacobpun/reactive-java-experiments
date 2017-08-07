package org.pk.observable;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.observables.ConnectableObservable;

public class HotObservableShare {

	public static void main(String[] args) throws InterruptedException {
		Observable<Long>  hotObservable = Observable.interval(100L, TimeUnit.MILLISECONDS).publish().refCount();
		
		// This is also same --- share() is a shortcut for publish().refCount()
		// Observable<Long>  hotObservable = Observable.interval(100L, TimeUnit.MILLISECONDS).share();

		Subscription first = print(hotObservable, "First");
		Subscription second = print(hotObservable, "Second");
		Thread.sleep(1000);
		first.unsubscribe();
		second.unsubscribe();
				
		Thread.sleep(1000);
		Subscription third = print(hotObservable, "Third");
		Thread.sleep(1000);
		third.unsubscribe();
	}

	private static Subscription print(Observable<?> hotInterval,
			String prefix) {
		return hotInterval.subscribe(data -> System.out.println(prefix + ": " +  data));
	}	
}