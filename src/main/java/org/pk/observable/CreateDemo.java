package org.pk.observable;

import java.util.Arrays;
import java.util.Iterator;

import rx.Observable;
import rx.Subscriber;

public class CreateDemo {
	public static void main(String[] args) {
		fomIterable(Arrays.asList("a", "b", "c")).subscribe(System.out::println);
	}

	public static <T>  Observable<T> fomIterable(Iterable<T> iterable) {
		return Observable.unsafeCreate(subscriber -> processIterable(subscriber, iterable));
	}

	public static <T>  void processIterable(Subscriber<? super T> subscriber, Iterable<T> iterable) {
		Iterator<T> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			subscriber.onNext(iterator.next());
		}
		subscriber.onCompleted();
	}
}
