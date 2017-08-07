package org.pk.observable;

import rx.Observable;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class ObservableSum2 {

	private BehaviorSubject<Double> a = BehaviorSubject.create(0.00);
	private BehaviorSubject<Double> b = BehaviorSubject.create(0.00);
	private BehaviorSubject<Double> c = BehaviorSubject.create(0.0);
	
	public ObservableSum2() {
		Observable.combineLatest(a, b, (x, y) -> x+y).subscribe(c);
	}
	
	public static void main(String[] args) {
		ObservableSum2 o = new ObservableSum2();
		print(o.c, "a+b");
		o.a.onNext(10.00);
		o.b.onNext(12.00);
	}
	
	private static Subscription print(Observable<?> hotInterval,
			String prefix) {
		return hotInterval.subscribe(data -> System.out.println(prefix + ": " +  data));
	}	
}