package org.pk.observable;

import rx.Observable;

public class ZipExample {
	public static void main(String[] args) {
		Observable<Integer> o1 = Observable.range(1, 10);
		Observable<Integer> o2 = Observable.range(100, 20);

		Observable.zip(o1, o2, (a, b) -> a + b).subscribe(System.out::println);
	}
}