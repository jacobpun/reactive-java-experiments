package org.pk.observable;

import rx.Observable;
import rx.Subscription;

public class GroupByExample {
	public static void main(String[] args) {
		Observable.from("Dony:F, Nitya:F, Sneha:F, Punnoose:M".split(","))
		.map(String::trim)
		.map(nameAndGenderStr -> nameAndGenderStr.split(":"))
        .map(nameAndGenderArray -> new Person(nameAndGenderArray[0], nameAndGenderArray[1]))
        .groupBy(person -> person.gender)
        .subscribe(val -> print(val, val.getKey()));
		
	}
	
	private static Subscription print(Observable<?> obs,
			String prefix) {
		return obs.subscribe(data -> System.out.println(prefix + ": " +  data));
	}	
}
