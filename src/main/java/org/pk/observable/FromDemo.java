package org.pk.observable;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

import rx.Observable;

public class FromDemo {
	public static void main(String[] args) {
		
		// EXAMPLE 1 - from list
		List<String> list = Arrays.asList("blue", "red", "green", "yellow",
				"orange", "cyan", "purple");
		Observable<String> listObservable = Observable.from(list);
		listObservable.subscribe(System.out::println);
		System.out.println("\r\n***\r\n");
		listObservable.subscribe(color -> System.out.print( color + " | "));
		System.out.println("\r\n***\r\n");
		listObservable.subscribe(color -> System.out.print( color + " / "));
		System.out.println("\r\n***\r\n");
	
		// EXAMPLE 2 - from directory
		Path resources = Paths.get("src", "main", "java", "org" , "pk", "observable");
		try (DirectoryStream<Path> dStream = Files.newDirectoryStream(resources)) {
		  Observable<Path> dirObservable = Observable.from(dStream);
		  dirObservable.subscribe(System.out::println);
		} catch (IOException e) {
		  e.printStackTrace();
		}
	}
}