package org.pk.observable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;

public class ObservableSum {
	public static void main(String[] args) {
		ConnectableObservable<String> input = from(System.in);
		Observable<Double> a = varStream("a", input);
		Observable<Double> b = varStream("b", input);
		reactiveSum(a, b); 
		input.connect();
	}

	private static void reactiveSum(Observable<Double> a, Observable<Double> b) {
		Observable
			.combineLatest(a,b, (x, y) -> x+y)
			.subscribe(sum -> System.out.println("a+b = " + sum), 
					System.out::println);
	}

	private static Observable<Double> varStream(String prefix,
			ConnectableObservable<String> input) {
		final Pattern pattern =     Pattern.compile(
			      "\\s*" + prefix + "\\s*[:|=]\\s*(-?\\d+\\.?\\d*)$"
			    );
	    return input
	    	    .map(pattern::matcher)
	    	    .filter(m -> m.matches() && m.group(1) != null) 
	    	    .map(matcher -> matcher.group(1)) 
	    	    .map(Double::parseDouble); 
	}

	private static ConnectableObservable<String> from(InputStream in) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));		
		return Observable.<String>create(subscriber -> processInput(subscriber, reader)).publish();
	}
	
	private static void processInput(
			Subscriber<? super String> subscriber, BufferedReader reader) {
		String line;
		try {
			while((line = reader.readLine()) != null && !subscriber.isUnsubscribed()) {
				if (line.equalsIgnoreCase("exit")) {
					subscriber.unsubscribe();
					break;
				} else {
				    subscriber.onNext(line);
				}
			}
		} catch (Exception ex) {
			subscriber.onError(ex);
		}
		
		if (!subscriber.isUnsubscribed()) {
			subscriber.onCompleted();
		}
	}
}