package org.pk.observable;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class HotObservablePublishSubjectDemo {
	public static void main(String[] args) throws InterruptedException {
		Observable<Long> interval = Observable.interval(100L, TimeUnit.MILLISECONDS);
		Subject<Long, Long> publishable = PublishSubject.create();
		interval.subscribe(publishable);
		
		print(publishable, "First");
		print(publishable, "Second");
		Thread.sleep(1000);
		publishable.onNext(1000L);
		print(publishable, "Third");
		Thread.sleep(1000);
	
	}
	
	private static Subscription print(Observable<?> hotInterval,
			String prefix) {
		return hotInterval.subscribe(data -> System.out.println(prefix + ": " +  data));
	}	
}
