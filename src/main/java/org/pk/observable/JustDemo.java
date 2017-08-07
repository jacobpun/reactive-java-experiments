package org.pk.observable;

import rx.Observable;

public class JustDemo {
	public static void main(String[] args) {
		//EXAMPLE 1
		Observable.just("A string").subscribe(System.out::println);

		//EXAMPLE 2
		Observable.just('P', 'K', ' ', 'J', 'a', 'c', 'o', 'b').subscribe(System.out::print);

	}
}