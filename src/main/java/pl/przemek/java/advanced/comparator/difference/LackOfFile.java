package pl.przemek.java.advanced.comparator.difference;

import java.nio.file.Path;

public class LackOfFile extends FileDifference{

    private final Path notExistingFile;
    private boolean lackInFirstDir;

    public LackOfFile(Path notExistingFile, boolean lackInFirstDir) {
        this.notExistingFile = notExistingFile;
        this.lackInFirstDir = lackInFirstDir;
    }

    @Override
    protected Path getDifferentFile() {
        return notExistingFile;
    }

    @Override
    public String getMessage() {
        return String.format(
                "File: %s\tDir1: %s\t\t\t\t\t\t\tDir2: %s",
                notExistingFile,
                lackInFirstDir ? "absent" : "present",
                lackInFirstDir ? "present" : "absent"
        );
    }

}
