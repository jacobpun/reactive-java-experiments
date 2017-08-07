package org.pk.observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class FlatMapExample {
	public static void main(String[] args) {
		Observable<String> fileContents = listDirectory(Paths.get("src", "main", "java", "org", "pk", "observable"))
		                                      .flatMap(path -> viewFile(path));
		print(fileContents, "fileComtent: ");
		
		
		Observable<String> fileContentsWithFileName = listDirectory(Paths.get("src", "main", "java", "org", "pk", "observable"))
                .flatMap(path -> viewFile(path), (path, line) -> path.getFileName() + " : " + line);
		fileContentsWithFileName.subscribe(System.out::println);
	
	}

	private static Subscription print(Observable<?> observable,
			String prefix) {
		return observable.subscribe(data -> System.out.println(prefix + ": " +  data));
	}	
	
	private static Observable<Path> listDirectory(Path directory) {
		return Observable.<Path> create(subscriber -> processDirecory(subscriber, directory));
	}

	private static Observable<String> viewFile(Path file) {
		return Observable.<String> create(subsctiber -> viewFile(subsctiber, file));
	}

	private static void viewFile(Subscriber<? super String> subscriber, Path file) {
		try {
			BufferedReader reader = Files.newBufferedReader(file);
			subscriber.add(Subscriptions.create(() -> {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}));

			String line = null;
			while ((line = reader.readLine()) != null
					&& !subscriber.isUnsubscribed()) {
				subscriber.onNext(line);
			}

			if (!subscriber.isUnsubscribed()) {
				subscriber.onCompleted();
			}
		} catch (IOException ioe) {
			if (!subscriber.isUnsubscribed()) {
				subscriber.onError(ioe);
			}
		}
	}

	private static void processDirecory(Subscriber<? super Path> subscriber,
			Path directory) {
		try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(directory);
			subscriber.add(Subscriptions.create(() -> {
				try {
					stream.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}));
			Observable.<Path> from(stream).subscribe(subscriber);
		} catch (Exception ex) {
			subscriber.onError(ex);
		}
	}
}