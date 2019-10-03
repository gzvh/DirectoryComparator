package pl.przemek.java.advanced.comparator;

import pl.przemek.java.advanced.comparator.difference.FileDifference;

import java.nio.file.Paths;

public class App {

    public static void main(String[] args) {

        var start = System.currentTimeMillis();
        var differences = DirectoryComparator.compareDirs(
                Paths.get("dir1"),
                Paths.get("dir2")
        );
        System.out.println("Computed in: " + (System.currentTimeMillis() - start) + " millis");

        differences
                .stream()
                .sorted()
                .map(FileDifference::getMessage)
                .forEach(System.out::println);
    }

}

