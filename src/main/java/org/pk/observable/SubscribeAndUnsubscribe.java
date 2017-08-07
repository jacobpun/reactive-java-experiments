package org.pk.observable;

import java.util.Arrays;
import java.util.Iterator;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class SubscribeAndUnsubscribe {
	public static void main(String[] args) {
		Subscription subscription = fomIterable(Arrays.asList("This ", "text ", "will ", "not", "be ", "printed ", "fully."))
			.subscribeOn(Schedulers.computation())
			.subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {
				System.out.println("DONE!!");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("ERROR!!" + e);
				
			}

			@Override
			public void onNext(String data) {
				if(data.equals("not")){
					unsubscribe();
				} else {
					System.out.print(data);
				}
			}
		});
		
		subscription.unsubscribe();
	}
	
	public static <T>  Observable<T> fomIterable(Iterable<T> iterable) {
		return Observable.unsafeCreate(subscriber -> processIterable(subscriber, iterable));
	}

	public static <T>  void processIterable(Subscriber<? super T> subscriber, Iterable<T> iterable) {
		Iterator<T> iterator = iterable.iterator();
		while (iterator.hasNext() && !subscriber.isUnsubscribed()) {
			subscriber.onNext(iterator.next());
		}
		subscriber.onCompleted();
	}
}