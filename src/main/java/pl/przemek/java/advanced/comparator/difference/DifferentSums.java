package pl.przemek.java.advanced.comparator.difference;

import java.nio.file.Path;

public class DifferentSums extends FileDifference{

    private final Path differentFile;
    private final String firstFileChecksum;
    private final String secondFileChecksum;

    public DifferentSums(Path differentFile, String firstFileChecksum, String secondFileChecksum) {
        this.differentFile = differentFile;
        this.firstFileChecksum = firstFileChecksum;
        this.secondFileChecksum = secondFileChecksum;
    }

    @Override
    protected Path getDifferentFile() {
        return differentFile;
    }

    @Override
    public String getMessage() {
        return String.format(
                "File: %s\tDir1: %s\tDir2: %s",
                differentFile,
                firstFileChecksum,
                secondFileChecksum
        );
    }
}
