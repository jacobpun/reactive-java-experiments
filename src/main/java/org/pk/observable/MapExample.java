package org.pk.observable;

import rx.Observable;

public class MapExample {
	public static void main(String[] args) {
		Observable.from("Dony:F, Nitya:F, Sneha:F, Punnoose:M".split(","))
				.map(String::trim)
				.map(nameAndGenderStr -> nameAndGenderStr.split(":"))
		        .map(nameAndGenderArray -> new Person(nameAndGenderArray[0], nameAndGenderArray[1]))
		        .filter(MapExample::isFemale)
				.map(Person::getName)	
		 		.subscribe(System.out::println);
	}
	
	public static boolean isFemale(Person person) {
	 return person.gender.equalsIgnoreCase("F");	
	}
}

class Person {
	public String name;
	public String gender;
	
	Person(String name, String gender) {
		this.name = name;
		this.gender = gender;
	}
	
	public String getName() {
		return name;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String toString() {
		return "{ " + name + ": '" + gender + "'}";
	}
}