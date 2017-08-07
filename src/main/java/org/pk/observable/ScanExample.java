package org.pk.observable;

import rx.Observable;

public class ScanExample {

	public static void main(String[] args) {
		Observable.range(1, 10)
		          .doOnNext(val -> System.out.println("From Observable: " + val)) // peek
		          .scan((a, b) -> a + b)
		          .doOnNext(val -> System.out.println("After Scan: " + val))
		          .last()
		          .subscribe(val -> System.out.println("Final Sum: " + val));
	}

}
