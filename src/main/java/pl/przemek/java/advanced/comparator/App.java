package pl.przemek.java.advanced.comparator;

import pl.przemek.java.advanced.comparator.difference.FileDifference;

import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(7);

        var start = System.currentTimeMillis();
        var differences = DirectoryComparator.compareDirs(
                Paths.get("dir1"),
                Paths.get("dir2"),
                executorService
        );
        System.out.println("Computed in: " + (System.currentTimeMillis() - start) + " millis");

        differences
                .stream()
                .sorted()
                .map(FileDifference::getMessage)
                .forEach(System.out::println);
        executorService.shutdown();
    }

}

