package org.pk.observable;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;


/** DID NOT WORK AS EXPECTED **/

public class SwithMapExample {
	public static void main(String[] args) {
		createObservableWithTimeout(Arrays.asList("a", "b", "c", "d", "e", "f", "g"))
				.subscribeOn(Schedulers.newThread())
				.switchMap(val -> createObservableWithTimeout2(val))
				.subscribe(System.out::println);
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Observable<String> createObservableWithTimeout(List<String> list) {
		Observable<String> observable = Observable.<String>create(		
				subscriber -> {
					for(String item: list) {
						subscriber.onNext(item);
/*						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}*/
					}
				}
		);	
		return observable;
	}

	public static Observable<String> createObservableWithTimeout2(String val) {
		Observable<String> observable = Observable.<String>create(		
				subscriber -> {
					for(int i=0; i<100; i++) {
						subscriber.onNext(val + i);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
		);	
		return observable;
	}
}