package org.pk.observable;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.observables.ConnectableObservable;

public class HotObservableReplay {

	public static void main(String[] args) throws InterruptedException {
		ConnectableObservable<Long>  hotObservable = Observable.interval(100L, TimeUnit.MILLISECONDS).publish();

		Subscription first = print(hotObservable, "First");
		Subscription second = print(hotObservable, "Second");
		
		hotObservable.connect();
		
		Thread.sleep(1000);
		Subscription third = print(hotObservable, "Third");
		Thread.sleep(1000);
		
		first.unsubscribe();
		second.unsubscribe();
		third.unsubscribe();
	}

	private static Subscription print(ConnectableObservable<?> hotInterval,
			String prefix) {
		return hotInterval.subscribe(data -> System.out.println(prefix + ": " +  data));
	}	
}