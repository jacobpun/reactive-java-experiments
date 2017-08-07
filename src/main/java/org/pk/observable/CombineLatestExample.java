package org.pk.observable;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class CombineLatestExample {
	public static void main(String[] args) throws InterruptedException {
		Observable<String> greetings = Observable.zip(
					Observable.just("Hi", "Hello", "Namaste", "Good Morning", "Howdy", "Yo"),
					Observable.interval(10, TimeUnit.MILLISECONDS),
					(greeting, time) -> greeting
				);

		Observable<String> names = Observable.zip(
				Observable.just("Sneha", "Nitya", "Dony", "Punnoose"),
				Observable.interval(10, TimeUnit.MILLISECONDS),
				(greeting, time) -> greeting
			);
		
		Observable<String> exclamations = Observable.zip(
				Observable.just("!", "!!", "!!!"),
				Observable.interval(10, TimeUnit.MILLISECONDS),
				(greeting, time) -> greeting
			);
		
	
		Observable.combineLatest(greetings, names, exclamations, 
				(greeting, name, exclamation) -> greeting + " " + name + " " + exclamation)
				.subscribe(System.out::println);
		
		
		
		
		// print all combinations
		greetings.subscribe(
				greeting -> names.subscribe(
						name -> exclamations.subscribe(
								exclamation -> System.out.println("All combinations: " + greeting + " " + name + " " + exclamation)
								)
						)
				);
		
		
		//starts with
		exclamations
				  .startWith(names)
				  .startWith(greetings)
				  .subscribe(System.out::println);;
		
		Thread.sleep(10000);
	}
}
